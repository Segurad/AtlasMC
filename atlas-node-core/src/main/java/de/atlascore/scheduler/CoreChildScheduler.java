package de.atlascore.scheduler;

import de.atlasmc.scheduler.Scheduler;

public class CoreChildScheduler extends CoreAbstractScheduler {

	private final Scheduler parent;
	
	public CoreChildScheduler(Scheduler parent) {
		this.parent = parent;
		parent.addChild(this);
	}

	@Override
	public Scheduler createScheduler() {
		return new CoreChildScheduler(this);
	}

	@Override
	public void shutdown() {
		super.shutdown();
		parent.removeChild(this);
	}

}
