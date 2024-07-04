package de.atlascore.scheduler;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.scheduler.AtlasTask;

class CoreRepeatingTask extends CoreRegisteredTask {

	private final long period;
	private long delay;
	
	public CoreRepeatingTask(Plugin plugin, AtlasTask task, long delay, long period) {
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
