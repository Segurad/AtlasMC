package de.atlasmc.util;

import de.atlasmc.util.annotation.NotNull;

public interface Builder<T> {
	
	@NotNull
	T build();

}
