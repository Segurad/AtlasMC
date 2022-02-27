package de.atlascore.scheduler;

import de.atlasmc.scheduler.AtlasTask;

class CoreDelayedTask extends CoreRegisteredTask {

	private long delay;
	
	public CoreDelayedTask(AtlasTask task, long delay) {
		super(task);
		this.delay = delay;
	}

	@Override
	public void tick() {
		delay--;
	}
	
	@Override
	public boolean isDead() {
		return super.isDead() || delay <= 0;
	}

	@Override
	public boolean isRunnable() {
		return delay <= 0;
	}

}
