package de.atlasmc.cache;

import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public final class Caching {
	
	private static CacheHandler handler;
	
	private Caching() {}
	
	public static boolean register(CacheHolder holder) {
		return handler.register(holder);
	}
	
	public static boolean register(CacheHolder holder, int intervall) {
		return handler.register(holder, intervall);
	}
	
	public static boolean unregister(CacheHolder holder) {
		return handler.unregister(holder);
	}

	public static void cleanUpNow() {
		handler.cleanUpNow();
	}
	
	/**
	 * {@link CacheHandler#getDefaultIntervall()}
	 */
	public static long getDefaultIntervall() {
		return handler.getDefaultIntervall();
	}
	
	/**
	 * {@link CacheHandler#setDefaultIntervall(int)}
	 */
	public static void setDefaultIntervall(int intervall) {
		handler.setDefaultIntervall(intervall);
	}
	
	@InternalAPI
	public static void init(CacheHandler handler) {
		if (handler == null)
			throw new IllegalArgumentException("Handler can not be null!");
		if (Caching.handler != null)
			throw new IllegalStateException("Cache already initialized!");
		synchronized (Caching.class) {
			if (Caching.handler != null)
				throw new IllegalStateException("Cache already initialized!");
			Caching.handler = handler;
		}
	}
	
}
