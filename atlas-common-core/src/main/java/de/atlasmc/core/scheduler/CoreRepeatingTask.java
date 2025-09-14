package de.atlasmc.core.scheduler;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.scheduler.AtlasTask;

class CoreRepeatingTask extends CoreRegisteredTask {

	private final long period;
	private long delay;
	
	public CoreRepeatingTask(PluginHandle plugin, AtlasTask task, long delay, long period) {
		super(plugin, task);
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
