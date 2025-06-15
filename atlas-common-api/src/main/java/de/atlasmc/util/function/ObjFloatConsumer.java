package de.atlasmc.util.function;

@FunctionalInterface
public interface ObjFloatConsumer<T> {
	
	void accept(T t, float value);

}
