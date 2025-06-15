package de.atlasmc.util.function;

@FunctionalInterface
public interface ToFloatFunction<T> {

	float applyAsFloat(T value);
	
}
