package de.atlascore.scheduler;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.scheduler.AtlasTask;

class CoreDelayedTask extends CoreRegisteredTask {

	private long delay;
	
	public CoreDelayedTask(PluginHandle plugin, AtlasTask task, long delay) {
		super(plugin, task);
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
