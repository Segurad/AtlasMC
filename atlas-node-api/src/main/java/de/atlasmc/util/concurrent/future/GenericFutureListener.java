package de.atlasmc.util.concurrent.future;

public interface GenericFutureListener<F extends Future<?>> {
	
	void complete(F future);

}
