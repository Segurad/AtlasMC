package de.atlasmc.util.concurrent.future;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public interface Future<V> extends java.util.concurrent.Future<V> {
	
	boolean isCancellable();
	
	V getNow();
	
	Throwable cause();
	
	boolean isSuccess();
	
	void setListener(FutureListener<V> listener);
	
	void removeListener(FutureListener<V> listener);
	
	public static <V> Collection<V> getFutureResults(Collection<Future<V>> futures) {
		CumulativeFuture<V> cumFuture = new CumulativeFuture<>(futures);
		Collection<Future<V>> finishedFutures = null;
		try {
			finishedFutures = cumFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new FutureException("Error while awaiting result!", e);
		}
		ArrayList<V> results = new ArrayList<>(); 
		for (Future<V> future : finishedFutures) {
			V value = future.getNow();
			if (value == null)
				continue;
			results.add(value);
		}
		return results;
	}

}
