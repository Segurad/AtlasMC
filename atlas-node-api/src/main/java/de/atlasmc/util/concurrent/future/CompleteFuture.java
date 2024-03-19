package de.atlasmc.util.concurrent.future;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public class CompleteFuture<V> implements Future<V> {
	
	private static final Future<?> NULL_RESULT_FUTURE = new CompleteFuture<>(null);
	private static final Future<?> EMPTY_LIST_FUTURE = new CompleteFuture<>(List.of());
	private static final Future<?> EMPTY_MAP_FUTURE = new CompleteFuture<>(Map.of());
	
	private final V result;
	private final boolean cancelled;
	private final Throwable cause;
	
	public CompleteFuture(V result) {
		this(result, false);
	}
	
	public CompleteFuture(V result, boolean cancelled) {
		this(result, null, cancelled);
	}
	
	public CompleteFuture(Throwable cause) {
		this(cause, false);
	}
	
	public CompleteFuture(Throwable cause, boolean cancelled) {
		this(null, cause, cancelled);
	}
	
	public CompleteFuture(boolean cancelled) {
		this(null, null, cancelled);
	}
	
	public CompleteFuture(V result, Throwable cause, boolean cancelled) {
		this.result = result;
		this.cause = cause;
		this.cancelled = cancelled;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return result;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return result;
	}

	@Override
	public V getNow() {
		return result;
	}

	@Override
	public Throwable cause() {
		return cause;
	}

	@Override
	public boolean isSuccess() {
		return result != null && cause == null && !cancelled;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setListener(GenericFutureListener<? extends Future<? super V>> listener) {
		((GenericFutureListener<Future<V>>) listener).complete(this);
	}

	@Override
	public boolean isCancellable() {
		return true;
	}
	
	/**
	 * Returns a successful Future of null
	 * @param <V>
	 * @return future
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	public static <V>  Future<V> getNullFuture() {
		return (Future<V>) NULL_RESULT_FUTURE;
	}
	
	/**
	 * Returns a successful Future with a empty immutable List
	 * @param <V>
	 * @return future
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	public static <V> Future<V> getEmptyListFuture() {
		return (Future<V>) EMPTY_LIST_FUTURE;
	}
	
	/**
	 * Returns a successful Future with a empty immutable Map
	 * @param <V>
	 * @return future
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	public static <V> Future<V> getEmptyMapFuture() {
		return (Future<V>) EMPTY_MAP_FUTURE;
	}

	@Override
	public void removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
		return;
	}

	/**
	 * Returns a Complete future of the given value.
	 * If the value is null {@link #getNullFuture()}.
	 * If the value is empty map {@link #getEmptyMapFuture()}.
	 * If the value is empty collection {@link #getEmptyListFuture()}.
	 * Otherwise a new CompleteFuture will be constructed.
	 * @param <V>
	 * @param value
	 * @return future
	 */
	@NotNull
	public static <V> Future<V> of(@Nullable V value) {
		if (value == null)
			return getNullFuture();
		if (value instanceof Collection<?> col && col.isEmpty()) {
			if (col instanceof Map)
				return getEmptyMapFuture();
			return getEmptyListFuture();
		}
		return new CompleteFuture<>(value);
	}

}
