package de.atlasmc.util.concurrent.future;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A future wrapper that will finish when all futures are complete
 * @param <V>
 */
public class CommulativeFuture<V> extends AbstractFuture<Collection<Future<V>>> {
	
	public CommulativeFuture(Collection<Future<V>> futures) {
		this.result = List.copyOf(futures);
		final AtomicInteger awaiting = new AtomicInteger(result.size());
		FutureListener<V> listener = (future) -> {
			if (awaiting.decrementAndGet() > 0)
				return;
			this.finish(result);
		};
		for (Future<V> future : result) {
			future.setListener(listener);
		}
	}

}
