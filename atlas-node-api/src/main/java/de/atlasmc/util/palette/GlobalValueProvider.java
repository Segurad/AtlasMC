package de.atlasmc.util.palette;

@FunctionalInterface
public interface GlobalValueProvider<E> {
	
	int value(E entry);

}
