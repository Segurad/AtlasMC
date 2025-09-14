package de.atlasmc.core.scheduler;

import de.atlasmc.scheduler.AtlasTask;

public class CoreAtlasTaskWrapper extends AtlasTask {
	
	private final Runnable runner;
	
	public CoreAtlasTaskWrapper(Runnable runner) {
		this.runner = runner;
	}

	@Override
	public void run() {
		runner.run();
	}

}
