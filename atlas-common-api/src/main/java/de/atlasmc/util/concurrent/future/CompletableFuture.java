package de.atlasmc.util.concurrent.future;

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
