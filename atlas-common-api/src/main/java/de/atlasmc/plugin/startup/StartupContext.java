package de.atlasmc.plugin.startup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import de.atlasmc.log.Log;
import de.atlasmc.plugin.AtlasModul;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.Multimap;

public class StartupContext {
	 
	/**
	 * Stage for initializing new stages
	 */
	public static final String INIT_STAGES = "init-stages";
	/**
	 * Stage for additional {@link Plugin}s
	 * <ul>
	 * <li>prepareStage - Register new {@link PluginLoader}</li>
	 * <li>handleStage - Load {@link Plugin}s</li>
	 * <li>prepareStage - {@link AtlasModul#initStartupHandler(StartupContext)}</li>
	 * </ul>
	 */
	public static final String LOAD_EXTRA_PLUGINS = "load-extra-plugins";
	/**
	 * Stage for initializing the Master.
	 * Only present if node is master.
	 */
	public static final String INIT_MASTER = "init-master";
	/**
	 * Stage for loading master data
	 */
	public static final String LOAD_MASTER_DATA = "load-master-data";
	/**
	 * Stage for connecting node to master
	 */
	public static final String CONNECT_MASTER = "connect-master"; 
    /**
     * Stage for initializing node
     */
	public static final String INIT_NODE = "init-node";
	
	/**
	 * Stage for loading node data
	 */
	public static final String LOAD_NODE_DATA = "load-node-data";
	
	/**
	 * Final stage for completing last initializations
	 */
	public static final String FINALIZE_STARTUP = "finalize-startup";
	
	private Multimap<String, StartupStageHandler> stageHandlers;
	private Set<Consumer<Throwable>> failHandlers;
	private Set<Consumer<String>> stageChangeHandlers;
	private List<String> stages;
	private List<String> stageView;
	private int stage = 0;
	private boolean failed;
	private Throwable cause;
	private Map<String, Object> context;
	private Map<String, Object> persistentContext;
	private Log logger;

	public StartupContext(Log logger) {
		this.logger = Objects.requireNonNull(logger);
		this.stageHandlers = new ArrayListMultimap<>();
		this.failHandlers = new HashSet<>();
		this.stageChangeHandlers = new HashSet<>();
		this.context = new HashMap<>();
		this.persistentContext = new HashMap<>();
		this.stages = new ArrayList<>();
	}
	
	/**
	 * Returns the logger used for startup
	 * @return logger
	 */
	@NotNull
	public Log getLogger() {
		return logger;
	}
	
	@Nullable
	public Object setContex(String key, Object value) {
		return context.put(key, value);
	}
	
	@Nullable
	public Object setPersistentContext(String key, Object value) {
		return persistentContext.put(key, value);
	}
	
	/**
	 * Returns the context
	 * @return context
	 */
	@NotNull
	public Map<String, Object> getContext() {
		return context;
	}
	
	/**
	 * Returns the persistent context
	 * @return persistent context
	 */
	@NotNull
	public Map<String, Object> getPersistentContext() {
		return persistentContext;
	}
	
	/**
	 * Returns the value of the given key or null if not present
	 * @param <T>
	 * @param key
	 * @return value of this key
	 * @throws ClassCastException if the returned type is not compatible
	 */
	@Nullable
	public <T> T getContext(String key) {
		Object raw = context.get(key);
		if (raw == null)
			return null;
		@SuppressWarnings("unchecked")
		T value = (T) raw;
		return value;
	}
	
	@Nullable
	public <T> T getPersistentContext(String key) {
		Object raw = persistentContext.get(key);
		if (raw == null)
			return null;
		@SuppressWarnings("unchecked")
		T value = (T) raw;
		return value;
	}
	
	/**
	 * Returns the current stage
	 * @return stage
	 */
	@NotNull
	public String getStage() {
		return stages.get(stage);
	}
	
	/**
	 * Whether or not more stages are present
	 * @return true if has
	 */
	public boolean hasNextStage() {
		return stage + 1 < stages.size();
	}
	
	/**
	 * Moves the cursor to the next stage
	 * @return true if success
	 */
	public boolean nextStage() {
		if (!hasNextStage())
			return false;
		stage++;
		context.clear();
		return true;
	}
	
	/**
	 * Returns a collection of all handlers for the current stage
	 * @return handlers
	 */
	@NotNull
	public Collection<StartupStageHandler> getStageHandlers() {
		Collection<StartupStageHandler> handlers = stageHandlers.get(getStage());
		return handlers == null ? List.of() : handlers;
	}

	/**
	 * Returns a unmodifiable collection of that reflects all present stages
	 * @return stages
	 */
	@NotNull
	public List<String> getStages() {
		if (stageView == null)
			stageView = Collections.unmodifiableList(stages);
		return stageView;
	}

	public boolean addStage(String... stages) {
		if (stages == null || stages.length == 0)
			return false;
		boolean changes = false;
		for (String stage : stages) {
			if (this.stages.contains(stage))
				continue;
			if (!this.stages.add(stage))
				continue;
			changes = true;
			onNewStage(stage);
		}
		return changes;
	}
	
	private void onNewStage(String stage) {
		for (Consumer<String> handler : stageChangeHandlers)
			handler.accept(stage);
	}

	public boolean addStageBefore(String before, String... stages) {
		return addStage(before, true, stages);
	}

	public boolean addStageAfter(String after, String... stages) {
		return addStage(after, false, stages);
	}
	
	private boolean addStage(String stage, final boolean before, String[] stages) {
		if (stages == null || stages.length == 0)
			return false;
		int i = this.stages.indexOf(stage);
		if (i == -1 || this.stage > (before ? i-1 : i))
			return false;
		ArrayList<String> added = new ArrayList<>(stages.length);
		boolean changes = false;
		for (String s : stages) {
			if (this.stages.contains(stage))
				continue;
			if (before)
				this.stages.add(i++, s);
			else
				this.stages.add(++i, s);
			changes = true;
			added.add(s);
		}
		for (String add : added)
			onNewStage(add);
		return changes;
	}

	/**
	 * Returns whether or not the startup has failed
	 * @return true if failed
	 */
	public boolean hasFailed() {
		return failed;
	}
	
	/**
	 * Returns he failure cause
	 * @return cause
	 */
	@Nullable
	public Throwable getCause() {
		return cause;
	}

	/**
	 * Fails the startup by throwing a {@link StartupException}
	 */
	public void fail() {
		fail(null);
	}

	/**
	 * Fails the startup by throwing a {@link StartupException}
	 * @param cause
	 */
	public void fail(Throwable cause) {
		if (failed)
			throw new IllegalStateException("Startup already failed!");
		this.failed = true;
		this.cause = cause;
		throw new StartupException("Failed startup in stage: " + getStage(), cause);
	}
	
	public void addStageHandler(String stage, StartupStageHandler handler) {
		if (stage == null)
			throw new IllegalArgumentException("Stage can not be null!");
		if (handler == null)
			throw new IllegalArgumentException("Handler can not be null!");
		stageHandlers.put(stage, handler);
	}
	
	public void removeStageHandler(String stage, StartupStageHandler handler) {
		stageHandlers.remove(stage, handler);
	}
	
	public void addFailHandler(Consumer<Throwable> handler) {
		failHandlers.add(handler);
	}
	
	public void removeFailHandler(Consumer<Throwable> handler) {
		failHandlers.remove(handler);
	}
	
	public void addStageChangeHandler(Consumer<String> handler) {
		stageChangeHandlers.add(handler);
	}
	
	public void removeStageChangeHandler(Consumer<String> handler) {
		stageChangeHandlers.remove(handler);
	}

	public Collection<Consumer<Throwable>> getFailHandlers() {
		return failHandlers;
	}
	
}
