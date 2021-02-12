package de.atlasmc.tag;

import java.util.Set;

public interface Tag<T> {
	
	public boolean isTaged(T element);
	public Set<T> getValues();

}
