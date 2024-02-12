package de.atlasmc.util.concurrent.future;

public interface Future<V> extends java.util.concurrent.Future<V> {
	
	boolean isCancellable();
	
	V getNow();
	
	Throwable cause();
	
	boolean isSuccess();
	
	void setListener(GenericFutureListener<? extends Future<? super V>> listener);
	
	void removeListener(GenericFutureListener<? extends Future<? super V>> listener);

}
