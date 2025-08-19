package de.atlasmc.util;

import java.util.TimerTask;

public final class DummyTask extends TimerTask {
	
	private final Runnable task;

	public DummyTask(Runnable task) {
		if (task == null)
			throw new IllegalArgumentException("Task can not be null!");
		this.task = task;
	}

	@Override
	public void run() {
		task.run();
	}
}
