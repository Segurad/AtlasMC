package de.atlasmc.node.util.palette;

@FunctionalInterface
public interface GlobalValueProvider<E> {
	
	int value(E entry);

}
