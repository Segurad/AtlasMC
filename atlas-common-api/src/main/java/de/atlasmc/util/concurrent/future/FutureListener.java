package de.atlasmc.util.concurrent.future;

public interface FutureListener<V> {
	
	void complete(Future<V> future);

}
