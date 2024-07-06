package de.atlascore;

import de.atlasmc.log.Log;
import de.atlasmc.tick.AtlasThread;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.TickingThread;

public class CoreAtlasThread extends TickingThread implements AtlasThread {
	
	public CoreAtlasThread(Log logger) {
		super("Atlas-Main", 50, logger, false);
	}
	
	@Override
	public void run() {
		super.run();
	}
	
	@Override
	protected void tick(int tick) {
		
	}

	@Override
	public boolean addStartupHook(AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBeforeStartupHook(String taskName, AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAfterStartupHook(String taskName, AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeStartupHook(AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTickTask(AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBeforeTickTask(String taskName, AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAfterTickTask(String taskName, AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTickTask(AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addShutdownHook(AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBeforeShutdownHook(String taskName, AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAfterShutdownHook(String taskName, AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeShutdownHook(AtlasThreadTask task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startThread() {
		start();
	}

}
