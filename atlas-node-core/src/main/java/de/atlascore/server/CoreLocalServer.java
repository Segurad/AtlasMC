package de.atlascore.server;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.NodePlayer;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.event.Event;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.LocalServer;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.ServerException;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.world.World;

public class CoreLocalServer extends CoreAbstractNodeServer implements LocalServer {
	
	private static final List<NamespacedKey> TASKS_ON_STARTUP;
	private static final List<NamespacedKey> TASKS_ON_PREPARATION;
	private static final List<NamespacedKey> TASKS_ON_SHUTDOWN;
	private static final List<NamespacedKey> TASKS_ON_TICK;
	
	static {
		TASKS_ON_STARTUP = List.of();
		TASKS_ON_PREPARATION = List.of();
		TASKS_ON_SHUTDOWN = List.of();
		TASKS_ON_TICK = List.of();
	}
	
	private final Set<NodePlayer> players;
	private final Set<World> worlds;
	private CoreServerThread thread;
	private final Log logger;
	private volatile Status targetStatus;
 	private volatile Future<Boolean> future;
 	private final LinkedHashSet<Consumer<NodeServer>> shutdownHooks;
	protected final Lock lock = new ReentrantLock();
	
	public CoreLocalServer(UUID serverID, File workdir, ServerGroup group) {
		this(serverID, workdir, group, group.getServerConfig().clone());
	}
	
	public CoreLocalServer(UUID serverID, File workdir, ServerConfig config) {
		this(serverID, workdir, null, config);
	}
	
	protected CoreLocalServer(UUID serverID, File workdir, ServerGroup group, ServerConfig config) {
		super(serverID, workdir, new File(workdir, "worlds/"), group, config);
		this.players = new HashSet<>();
		this.worlds = new HashSet<>();
		this.shutdownHooks = new LinkedHashSet<>();
		this.logger = Logging.getLogger(getServerName(), "Server");
	}

	@Override
	public String getImplementationName() {
		return "Atlas-Internal";
	}

	@Override
	public Collection<NodePlayer> getPlayers() {
		return players;
	}

	@Override
	public int getPlayerCount() {
		return players.size();
	}

	@Override
	public int getMaxPlayers() {
		return config.getSlots();
	}

	@Override
	public void queueEvent(Event event) {
		thread.queueEvent(event);
	}

	@Override
	public Collection<World> getWorlds() {
		return worlds;
	}

	@Override
	public Scheduler getScheduler() {
		return thread.getScheduler();
	}

	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == thread;
	}

	@Override
	public void runTask(Runnable task) {
		if (thread == null)
			throw new ServerException("Server not running: " + status);
		thread.runTask(task);
	}
	
	public ServerConfig getConfig() {
		return config;
	}

	@Override
	public Future<Boolean> start() {
		Future<Boolean> future = tryTarget(Status.ONLINE, Status.AWAIT_START);
		if (future != null)
			return future;
		lock.lock();
		future = tryTarget(Status.ONLINE, Status.AWAIT_START);
		if (future != null) {
			lock.unlock();
			return future;
		}
		status = Status.STARTUP;
		logger.info("Starting...");
		CoreServerThread thread = this.thread = new CoreServerThread(this, shutdownHooks);
		this.future = future = thread.startServer();
		lock.unlock();
		return future;
	}
	
	private Future<Boolean> tryTarget(Status target, Status required) {
		Status status = this.status;
		if (status == target)
			return CompleteFuture.getTrueFuture();
		if (status.ordinal() < required.ordinal())
			return new CompleteFuture<>(false, new ServerException("Server is not in required status ("+ required +") was: " + status));
		Status currentTarget = this.targetStatus;
		if (currentTarget == target) {
			return this.future;
		} else if (currentTarget == null) {
			return null;
		}
		return new CompleteFuture<>(false, new ServerException("Server currently targets another status: " + currentTarget));
	}

	@Override
	public Future<Boolean> stop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> prepare() {
		Future<Boolean> future = tryTarget(Status.AWAIT_START, Status.OFFLINE);
		if (future != null)
			return future;
		lock.lock();
		future = tryTarget(Status.AWAIT_START, Status.OFFLINE);
		if (future != null) {
			lock.unlock();
			return future;
		}
		status = Status.PREPARATION;
		logger.info("Preparing...");
		CoreLocalServerPreparingTask task = new CoreLocalServerPreparingTask(this);
		this.future = future = task.getFuture();
		targetStatus = Status.AWAIT_START;
		future.setListener((f) -> {
			lock.lock();
			if (f.getNow()) {
				if (status == Status.PREPARATION) {
					this.status = Status.AWAIT_START;
				}
			} else {
				logger.info("Failed preparation!", f.cause());
				this.status = Status.OFFLINE;
			}
			this.targetStatus = null;
			this.future = null;
			lock.unlock();
		});
		Atlas.getScheduler().runAsyncTask(Atlas.getSystem(), task);
		lock.unlock();
		return future;
	}
	
	protected void onShutdown() {
		synchronized (shutdownHooks) {
			for (Consumer<NodeServer> c : shutdownHooks.reversed()) {
				try {
					c.accept(this);
				} catch(Exception e) {
					logger.error("Error in shutdown hook!", e);
				}
			}
		}
	}
	
	protected void onStartup() {
		if (config.hasPlugins()) {
			Map<NamespacedKey, NamespacedKey> plugins = config.getPlugins();
			DataRepositoryHandler handler = Atlas.getDataHandler();
			Future<Collection<RepositoryEntry>> pluginEntries = handler.getEntries(plugins.keySet());
			Future<Collection<RepositoryEntry>> cfgEntries = handler.getEntries(plugins.values());
			try {
				
			} catch (Exception e) {
				logger.error("Error while fetching plugins!", e);
			}
		}
	}

	@Override
	public boolean addShutdownHook(Consumer<NodeServer> hook) {
		synchronized (shutdownHooks) {
			return shutdownHooks.add(hook);
		}
	}

	@Override
	public boolean removeShutdownHook(Consumer<NodeServer> hook) {
		synchronized (shutdownHooks) {
			return shutdownHooks.remove(hook);
		}
	}

}
