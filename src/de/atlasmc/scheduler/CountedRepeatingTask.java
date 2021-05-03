package de.atlasmc.scheduler;

class CountedRepeatingTask extends RegisteredTask {

	private final long period;
	private long delay, repeats;
	
	public CountedRepeatingTask(AtlasTask task, long delay, long period, long repeats) {
		super(task);
		this.period = period-1;
		this.delay = delay;
		this.repeats = repeats;
	}

	@Override
	public void tick() {
		if (delay <= 0) {
			delay = period;
			repeats--;
		}
		delay--;
	}

	@Override
	public boolean unregister() {
		return super.unregister() || repeats <= 0;
	}
	
	@Override
	public boolean isRunnable() {
		return delay <= 0;
	}

}
