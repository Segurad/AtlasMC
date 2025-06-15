package de.atlasmc.util.function;

@FunctionalInterface
public interface ObjShortConsumer<T> {
	
	void accept(T t, short value);

}
