package de.atlasmc.util.concurrent.future;

@FunctionalInterface
public interface FutureListener<V> {
	
	void complete(Future<V> future);

}
