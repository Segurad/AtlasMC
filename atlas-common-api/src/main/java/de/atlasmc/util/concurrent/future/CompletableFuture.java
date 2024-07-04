package de.atlasmc.util.concurrent.future;

public class CompletableFuture<V> extends AbstractFuture<V> {
	
	@Override
	public void finish(V result) {
		super.finish(result, null);
	}
	
	@Override
	public void finish(V result, Throwable cause) {
		super.finish(result, cause);
	}

	
}
