package de.atlasmc.util.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {
	
	boolean applyAsBoolean(T value);

}
