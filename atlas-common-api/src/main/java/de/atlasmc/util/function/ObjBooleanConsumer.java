package de.atlasmc.util.function;

@FunctionalInterface
public interface ObjBooleanConsumer<T> {

	void accept(T t, boolean value);
	
}
