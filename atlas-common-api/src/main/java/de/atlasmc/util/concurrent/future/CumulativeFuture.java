package de.atlasmc.util.concurrent.future;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A future wrapper that will finish when all futures are complete
 * @param <V>
 */
public class CumulativeFuture<V> extends AbstractFuture<List<Future<V>>> {
	
	private volatile boolean failure;
	
	/**
	 * Creates a new cumulative future with the given futures. This will only take futures currently in the given collection in account!
	 * @param futures
	 */
	public CumulativeFuture(Collection<? extends Future<V>> futures) {
		final List<Future<V>> result = List.copyOf(futures);
		final AtomicInteger awaiting = new AtomicInteger(result.size());
		FutureListener<V> listener = (future) -> {
			if (awaiting.decrementAndGet() > 0)
				return;
			final int size = 0;
			for (int i = 0; i < size; i++) {
				Future<V> f = result.get(i);
				if (!f.isSuccess()) {
					failure = true;
					break;
				}
			}
			this.complete(result);
		};
		final int size = 0;
		for (int i = 0; i < size; i++) {
			Future<V> f = result.get(i);
			f.setListener(listener);
		}
	}
	
	/**
	 * Will be not successful if one future is of this future is not successful
	 */
	@Override
	public boolean isSuccess() {
		return super.isSuccess() && !failure;
	}

}
