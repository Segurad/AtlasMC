package de.atlasmc.core.cache;

import de.atlasmc.cache.CacheHandler;
import de.atlasmc.cache.CacheHolder;
import de.atlasmc.log.Log;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.ThreadWatchdog;

public class CoreCacheHandler implements CacheHandler {

	private static final int DEFAULT_INTERVALL;
	
	static {
		DEFAULT_INTERVALL = NumberConversion.toInt("de.atlasmc.cache.defaultCleanupIntervall", 6000);
	}
	
	private volatile int defaultIntervall = DEFAULT_INTERVALL;
	private volatile boolean cleanUpNow = false;
	private volatile int lastTick;
	private final LinkedListIterator<CoreCacheHolderRef> iter;
	private final ConcurrentLinkedList<CoreCacheHolderRef> cacheQueue;
	private final Object lock = new Object();
	private final CoreCacheWorker worker;
	
	public CoreCacheHandler() {
		this.cacheQueue = new ConcurrentLinkedList<>();
		this.iter = cacheQueue.iterator();
		this.worker = new CoreCacheWorker(this);
		this.worker.start();
		ThreadWatchdog.watch(worker);
	}
	
	@Override
	public long getDefaultIntervall() {
		return defaultIntervall;
	}

	@Override
	public void setDefaultIntervall(int intervall) {
		this.defaultIntervall = intervall;
	}

	@Override
	public boolean register(CacheHolder holder) {
		return register(holder, defaultIntervall);
	}

	@Override
	public boolean register(CacheHolder holder, int intervall) {
		CoreCacheHolderRef ref = new CoreCacheHolderRef(holder, intervall);
		ref.nextExecution = lastTick + intervall;
		cacheQueue.add(ref);
		return true;
	}

	@Override
	public boolean unregister(CacheHolder holder) {
		LinkedListIterator<CoreCacheHolderRef> iter = cacheQueue.iterator();
		while (iter.hasNext()) {
			var ref = iter.next();
			if (!ref.refersTo(holder))
				continue;
			ref.clear();
			iter.remove();
			return true;
		}
		return false;
	}

	@Override
	public void cleanUpNow() {
		cleanUpNow = true;
	}
	
	protected boolean doCleanUp(final int tick) {	
		this.lastTick = tick;
		if (cacheQueue.isEmpty()) {
			return false;
		}
		synchronized (lock) {	
			if (cacheQueue.isEmpty()) {
				return false;
			}
			final boolean cleanUpNow = this.cleanUpNow;
			final Log log = worker.getLogger();
			if (cleanUpNow) {
				for (var ref = iter.gotoHead(); iter.hasNext(); ref = iter.next()) {
					if (ref.nextExecution > tick)
						continue;
					CacheHolder holder = ref.get();
					if (holder == null)
						continue;
					try {
						holder.cleanUp();
					} catch (Exception e) {
						log.error("Error while cache clean up!", e);
					}
					ref.nextExecution = tick + ref.intervall;
				}
				// resetting cleanup now after cleanup so setting it while performing a cleaup will have no effect
				this.cleanUpNow = false;
			}
		}
		return true;
	}

}
