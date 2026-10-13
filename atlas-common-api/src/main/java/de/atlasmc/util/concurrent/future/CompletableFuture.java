package de.atlasmc.util.concurrent.future;

/**
 * Future implementation that will be completed by a external cause
 * @param <V>
 */
public class CompletableFuture<V> extends AbstractFuture<V> {
	
	@Override
	public void complete(V result) {
		super.complete(result, null);
	}
	
	@Override
	public void complete(V result, Throwable cause) {
		super.complete(result, cause);
	}

	
}
