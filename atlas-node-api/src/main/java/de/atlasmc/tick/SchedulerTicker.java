package de.atlasmc.tick;

import de.atlasmc.scheduler.Scheduler;

public class SchedulerTicker implements Ticker<Scheduler> {

	public static final SchedulerTicker DEFAULT = new SchedulerTicker();
	
	@Override
	public void tick(Scheduler value) {
		value.runNextTasks();
	}

}
