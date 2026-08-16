package de.atlasmc.cache;

import de.atlasmc.util.annotation.ThreadSafe;

public interface CacheHolder {
	
	public static final int DEFAULT_TTL = Integer.getInteger("de.atlasmc.cache.defaultTTL", 6000);

	@ThreadSafe
	void cleanUp();

}
