package de.atlasmc.util;

import java.util.TimerTask;

public final class DummyTask extends TimerTask {
	private final Runnable task;

	public DummyTask(Runnable task) {
		this.task = task;
	}

	@Override
	public void run() {
		task.run();
	}
}
