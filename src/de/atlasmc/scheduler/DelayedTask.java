package de.atlasmc.scheduler;

class DelayedTask extends RegisteredTask {

	private long delay;
	
	public DelayedTask(AtlasTask task, long delay) {
		super(task);
		this.delay = delay;
	}

	@Override
	public void tick() {
		delay--;
	}
	
	@Override
	public boolean unregister() {
		return super.unregister() || delay <= 0;
	}

	@Override
	public boolean isRunnable() {
		return delay <= 0;
	}

}
