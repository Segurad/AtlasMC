package de.atlasmc.tick;

import de.atlasmc.util.WatchableThread;

public interface AtlasThread extends WatchableThread {
	
	boolean addStartupHook(AtlasThreadTask task);
	
	boolean addBeforeStartupHook(String taskName, AtlasThreadTask task);
	
	boolean addAfterStartupHook(String taskName, AtlasThreadTask task);
	
	boolean removeStartupHook(AtlasThreadTask task);
	
	boolean addTickTask(AtlasThreadTask task);
	
    boolean addBeforeTickTask(String taskName, AtlasThreadTask task);
	
	boolean addAfterTickTask(String taskName, AtlasThreadTask task);
	
	boolean removeTickTask(AtlasThreadTask task);
	
	boolean addShutdownHook(AtlasThreadTask task);
	
	boolean addBeforeShutdownHook(String taskName, AtlasThreadTask task);
	
	boolean addAfterShutdownHook(String taskName, AtlasThreadTask task);
	
	boolean removeShutdownHook(AtlasThreadTask task);
	
	boolean isRunning();
	
	void startThread();
	
	void stopThread();

	void runTask(Runnable task);

}
