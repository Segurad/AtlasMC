package de.atlasmc.core.node.server;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlasmc.Atlas;
import de.atlasmc.event.Event;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.server.ServerException;
import de.atlasmc.node.world.World;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.tick.AtlasThread;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.tick.AtlasThreadTaskManager;
import de.atlasmc.util.Pair;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CoreLocalServer extends CoreAbstractNodeServer implements LocalServer {
	
	private static final List<Pair<String, String>> TASKS_ON_STARTUP;
	private static final List<Pair<String, String>> TASKS_ON_SHUTDOWN;
	private static final List<Pair<String, String>> TASKS_ON_TICK;
	
	static {
		TASKS_ON_STARTUP = List.of();
		TASKS_ON_SHUTDOWN = List.of();
		TASKS_ON_TICK = List.of(
				Pair.of("sync-events", "atlas-core:server/tick/events"),
				Pair.of("sync-connection", "atlas-core:server/tick/connection"),
				Pair.of("tick-worlds", "atlas-core:server/tick/worlds"),
				Pair.of("sync-scheduler", "atlas-core:server/tick/scheduler")
				);
	}
	
	private final Queue<Event> eventQueue;
	private final Set<NodePlayer> players;
	private final Set<World> worlds;
	private AtlasThread<CoreLocalServer> thread;
	private Scheduler scheduler;
	private final Log logger;
	private volatile Status targetStatus;
 	private volatile Future<Boolean> future;
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
		this.eventQueue = new ConcurrentLinkedQueue<>();
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
		eventQueue.add(event);
	}

	@Override
	public Collection<World> getWorlds() {
		return worlds;
	}

	@Override
	public Scheduler getScheduler() {
		return scheduler;
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
		AtlasThread<CoreLocalServer> thread = this.thread = new AtlasThread<>(this.getServerName(), 50, logger, false,  this);
		logger.debug("Initializing startup hooks...");
		buildTasks(thread.getStartupTasks(), TASKS_ON_STARTUP, "startup-tasks");
		logger.debug("Initializing shutdown hooks...");
		buildTasks(thread.getShutdownTasks(), TASKS_ON_SHUTDOWN, "shutdown-hooks");
		logger.debug("Initializing tick tasks...");
		buildTasks(thread.getTickTasks(), TASKS_ON_TICK, "tick-tasks");
		this.future = future = thread.startThread();
		future.setListener((_) -> {
			lock.lock();
			logger.info("Server online...");
			this.status = Status.ONLINE;
			this.targetStatus = null;
			this.future = null;
			lock.unlock();
		});
		lock.unlock();
		return future;
	}
	
	private <T> void buildTasks(AtlasThreadTaskManager<T> taskManager, List<Pair<String, String>> defaults, String tasksKey) {
		ConfigurationSection cfg = getConfig().getConfig().getConfigurationSection(tasksKey);
		if (cfg == null) {
			if (defaults.isEmpty())
				return;
			buildDefaultTasks(defaults, taskManager);
			return;
		}
		Registry<AtlasThreadTaskFactory> registry = Registries.getRegistry(AtlasThreadTaskFactory.class);
		for (Entry<String, Object> entry : cfg.asMap().entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			// checking for defaults
			if (name.equals("defaults")) {
				if (Boolean.TRUE != value)
					continue;
				buildDefaultTasks(defaults, taskManager);
				continue;
			}
			// building task
			String key = null;
			ConfigurationSection taskCfg = null;
			if (value instanceof String str) {
				key = str;
				taskCfg = Configuration.of();
			} else if (value instanceof ConfigurationSection section) {
				taskCfg = section;
				key = section.getString("type");
			}
			if (key == null) {
				logger.warn("Unable to locate task key for task: {}", name);
				continue;
			}
			AtlasThreadTaskFactory factory = registry.get(key);
			if (factory == null) {
				logger.warn("Missing task factory: {}", key);
				continue;
			}
			try {
				AtlasThreadTask<T> task = factory.createTask(taskCfg);
				taskManager.addTask(name, task);
			} catch(Exception e) {
				logger.error("Error while creating task: " + key, e);
			}
		}
	}
	
	private <T> void buildDefaultTasks(List<Pair<String, String>> defaults, AtlasThreadTaskManager<T> taskManager) {
		Registry<AtlasThreadTaskFactory> registry = Registries.getRegistry(AtlasThreadTaskFactory.class);
		for (Pair<String, String> rawTask : defaults) {
			String key = rawTask.value2();
			AtlasThreadTaskFactory factory = registry.get(key);
			if (factory == null) {
				logger.warn("Missing default task factory: {}", key);
				continue;
			}
			AtlasThreadTask<T> task;
			try {
				task = factory.createTask(null);
			} catch(Exception e) {
				logger.error("Error while creating default task: " + key, e);
				continue;
			}
			if (!taskManager.addTask(rawTask.value1(), task))
				logger.warn("Failed to add default task: " + rawTask.value1());
		}
	}
	
	private Future<Boolean> tryTarget(Status target, Status required) {
		Status status = this.status;
		if (status == target)
			return CompleteFuture.getBooleanFuture(true);
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
		Future<Boolean> future = tryTarget(Status.OFFLINE, Status.ONLINE);
		if (future != null)
			return future;
		lock.lock();
		future = tryTarget(Status.OFFLINE, Status.ONLINE);
		if (future != null) {
			lock.unlock();
			return future;
		}
		status = Status.SHUTDOWN;
		logger.info("Shutting down...");
		this.scheduler.shutdown();
		this.scheduler = null;
		this.future = future = this.thread.stopThread();
		future.setListener((_) -> {
			lock.lock();
			logger.info("Server offline...");
			this.status = Status.OFFLINE;
			this.thread = null;
			this.targetStatus = null;
			this.future = null;
			lock.unlock();
		});
		lock.unlock();
		return future;
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

	@Override
	public Queue<Event> getEventQueue() {
		return eventQueue;
	}

}
