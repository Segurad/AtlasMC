package de.atlasmc.tick;

public interface ContextTickable<T> {
	
	void tick(T value);

}
