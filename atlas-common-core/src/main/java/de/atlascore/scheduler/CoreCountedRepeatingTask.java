package de.atlascore.scheduler;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.scheduler.AtlasTask;

class CoreCountedRepeatingTask extends CoreRegisteredTask {

	private final long period;
	private long delay, repeats;
	
	public CoreCountedRepeatingTask(PluginHandle plugin, AtlasTask task, long delay, long period, long repeats) {
		super(plugin, task);
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
	public boolean isDead() {
		return super.isDead() || repeats <= 0;
	}
	
	@Override
	public boolean isRunnable() {
		return delay <= 0;
	}

}
