package de.atlasmc.core.cache;

import java.lang.ref.WeakReference;

import de.atlasmc.cache.CacheHolder;

class CoreCacheHolderRef extends WeakReference<CacheHolder> {

	public final int intervall;
	public int nextExecution;
	
	public CoreCacheHolderRef(CacheHolder referent, int intervall) {
		super(referent);
		this.intervall = intervall;
	}

}
