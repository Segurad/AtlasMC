package de.atlasmc.util.concurrent.future;

public interface Future<V> extends java.util.concurrent.Future<V> {
	
	boolean isCancellable();
	
	V getNow();
	
	Throwable cause();
	
	boolean isSuccess();
	
	void setListener(FutureListener<V> listener);
	
	void removeListener(FutureListener<V> listener);

}
