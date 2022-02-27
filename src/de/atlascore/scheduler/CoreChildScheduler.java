package de.atlascore.scheduler;

import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.ConcurrentLinkedList.EntryPointer;

public class CoreChildScheduler extends CoreAbstractScheduler {

	private final EntryPointer<Scheduler> pointer;
	
	public CoreChildScheduler(CoreAbstractScheduler parent) {
		pointer = parent.addChild(this);
	}

	@Override
	public Scheduler createScheduler() {
		return new CoreChildScheduler(this);
	}

	@Override
	public void shutdown() {
		super.shutdown();
		pointer.remove();
	}

}
