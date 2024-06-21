package de.atlasmc.cache;

import de.atlasmc.util.annotation.ThreadSafe;

public interface CacheHolder {

	@ThreadSafe
	void cleanUp();

}
