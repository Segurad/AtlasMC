package de.atlascore.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import de.atlasmc.cache.CacheHolder;

class CoreCacheHolderRef extends WeakReference<CacheHolder> {

	public final int intervall;
	public int nextExecution;
	
	public CoreCacheHolderRef(CacheHolder referent, int intervall, ReferenceQueue<? super CacheHolder> q) {
		super(referent, q);
		this.intervall = intervall;
	}

}
