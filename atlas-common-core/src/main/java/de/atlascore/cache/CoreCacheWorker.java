package de.atlascore.cache;

import de.atlasmc.log.Logging;
import de.atlasmc.tick.TickingThread;

class CoreCacheWorker extends TickingThread {
	
	private final CoreCacheHandler handler;
	
	public CoreCacheWorker(CoreCacheHandler handler) {
		super("Atlas-Cache-Daemon", 50, Logging.getLogger("Cache", "Atlas"));
		this.handler = handler;
		setDaemon(true);
	}
	
	@Override
	protected void tick(int tick) {
		this.handler.doCleanUp(tick);
	}

}
