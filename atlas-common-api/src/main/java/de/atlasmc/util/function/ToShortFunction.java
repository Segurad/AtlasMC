package de.atlasmc.util.function;

@FunctionalInterface
public interface ToShortFunction<T> {

	short applyAsShort(T value);
	
}
