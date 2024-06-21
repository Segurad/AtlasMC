package de.atlascore.cache;

import de.atlasmc.log.Logging;
import de.atlasmc.util.TickingThread;

class CoreCacheWorker extends TickingThread {
	
	private final CoreCacheHandler handler;
	
	public CoreCacheWorker(CoreCacheHandler handler) {
		super("Cache-Worker", 50, Logging.getLogger("Cache", "Atlas"));
		this.handler = handler;
	}
	
	@Override
	protected void tick(int tick) {
		this.handler.doCleanUp(tick);
	}

}
