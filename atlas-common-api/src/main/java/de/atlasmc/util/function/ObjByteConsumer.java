package de.atlasmc.util.function;

@FunctionalInterface
public interface ObjByteConsumer<T> {
	
	void accept(T t, byte value);

}
