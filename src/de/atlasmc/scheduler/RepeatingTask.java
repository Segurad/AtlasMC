package de.atlasmc.scheduler;

class RepeatingTask extends RegisteredTask {

	private final long period;
	private long delay;
	
	public RepeatingTask(AtlasTask task, long delay, long period) {
		super(task);
		this.period = period-1;
		this.delay = delay;
	}

	@Override
	public void tick() {
		if (delay <= 0) delay = period;
		delay--;
	}

	@Override
	public boolean isRunnable() {
		return delay <= 0;
	}

}
