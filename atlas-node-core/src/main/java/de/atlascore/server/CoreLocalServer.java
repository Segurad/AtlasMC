package de.atlascore.server;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlascore.plugin.CorePluginManager;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.event.Event;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.LocalServer;
import de.atlasmc.server.ServerException;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.world.World;

public class CoreLocalServer extends CoreAbstractNodeServer implements LocalServer {
	
	private final Set<AtlasPlayer> players;
	private final Set<World> worlds;
	private CoreServerThread thread;
	private final Log logger;
	private volatile CompletableFuture<Boolean> future;
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
		this.logger = Logging.getLogger(this);
	}

	@Override
	public String getImplementationName() {
		return "Atlas-Internal";
	}

	@Override
	public Collection<AtlasPlayer> getPlayers() {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> stop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> prepare() {
		Future<Boolean> future = tryPreparation();
		if (future != null)
			return future;
		lock.lock();
		future = tryPreparation();
		if (future != null) {
			lock.unlock();
			return future;
		}
		status = Status.PREPARATION;
		logger.info("Preparing...");
		CoreLocalServerPreparingTask task = new CoreLocalServerPreparingTask(this);
		future = task.getFuture();
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
			this.future = null;
			lock.unlock();
		});
		Atlas.getScheduler().runAsyncTask(CorePluginManager.SYSTEM, task);
		lock.unlock();
		return future;
	}
	
	private Future<Boolean> tryPreparation() {
		Status status = this.status;
		if (status.ordinal() > Status.AWAIT_START.ordinal())
			throw new ServerException("Server is prepared");
		if (status == Status.AWAIT_START)
			return CompleteFuture.getTrueFuture();
		if (status == Status.PREPARATION)
			return future;
		return null;
	}

}
