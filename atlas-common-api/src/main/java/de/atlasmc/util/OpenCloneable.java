package de.atlasmc.util;

import de.atlasmc.util.annotation.NotNull;

/**
 * Interface for general clone access
 */
public interface OpenCloneable extends Cloneable {

	@NotNull
	Object clone();
	
}
