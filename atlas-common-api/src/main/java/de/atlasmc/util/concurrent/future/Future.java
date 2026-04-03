package de.atlasmc.util.concurrent.future;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import de.atlasmc.util.annotation.Nullable;

public interface Future<V> extends java.util.concurrent.Future<V> {
	
	
	boolean isCancellable();
	
	/**
	 * Returns the failure cause of this future
	 * @return cause
	 */
	@Nullable
	Throwable cause();
	
	/**
	 * Returns whether or not this future is successfully
	 * @return true if success
	 */
	boolean isSuccess();
	
	void setListener(FutureListener<V> listener);
	
	void removeListener(FutureListener<V> listener);
	
	@Override
	default State state() {
		if (!isDone())
			return State.RUNNING;
		if (isCancelled())
			return State.CANCELLED;
		return isSuccess() ? State.SUCCESS : State.FAILED;
	}
	
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
			V value = future.resultNow();
			if (value == null)
				continue;
			results.add(value);
		}
		return results;
	}

}
