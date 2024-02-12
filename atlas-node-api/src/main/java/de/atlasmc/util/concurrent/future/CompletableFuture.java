package de.atlasmc.util.concurrent.future;

import java.lang.reflect.Array;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFuture<V> implements Future<V> {

	protected volatile boolean done;
	protected volatile V result;
	protected volatile Throwable cause;
	protected volatile Object listener;
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}
	
	@Override
	public boolean isCancellable() {
		return false;	
	}

	@Override
	public boolean isDone() {
		return result != null;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		if (isDone())
			return result;
		synchronized (this) {
			if (isDone())
				return result;
			wait();
			Throwable cause = this.cause;
			if (cause != null)
				throw new ExecutionException(cause);
			return result;
		}
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (isDone())
			return result;
		synchronized (this) {
			if (isDone())
				return result;
			int nanos = 0;
			if (unit.ordinal() < TimeUnit.MILLISECONDS.ordinal()) {
				long val = TimeUnit.NANOSECONDS.convert(nanos, unit);
				nanos = (int) (val % 1000000);
			};
			wait(TimeUnit.MILLISECONDS.convert(timeout, unit), nanos);
			Throwable cause = this.cause;
			if (cause != null)
				throw new ExecutionException(cause);
			return result;
		}
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
		return result != null && cause == null && !isCancelled();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setListener(GenericFutureListener<? extends Future<? super V>> listener) {
		GenericFutureListener<Future<V>> newListener = (GenericFutureListener<Future<V>>) listener;
		if (isDone()) {
			newListener.complete(this);
			return;
		}
		synchronized (this) {
			if (isDone()) {
				newListener.complete(this);
				return;
			}
			Object currentListener = this.listener;
			if (currentListener == null) {
				this.listener = newListener;
			} else if (currentListener instanceof Array) {
				CopyOnWriteArraySet<Object> ary =  (CopyOnWriteArraySet<Object>) currentListener;
				ary.add(newListener);
			} else {
				CopyOnWriteArraySet<GenericFutureListener<Future<V>>> ary = new CopyOnWriteArraySet<>();
				ary.add((GenericFutureListener<Future<V>>) currentListener);
				ary.add(newListener);
				this.listener = ary;
			}
		}
	}
	
	@Override
	public void removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
		if (listener == null)
			return;
		if (isDone())
			return;
		synchronized (this) {
			if (isDone())
				return;
			Object currentListener = this.listener;
			if (currentListener == null) {
				return;
			} if (currentListener instanceof CopyOnWriteArraySet) {
				@SuppressWarnings("unchecked")
				CopyOnWriteArraySet<Object> ary = (CopyOnWriteArraySet<Object>) currentListener;
				ary.remove(listener);
			} else if (currentListener == listener)
				this.listener = null;
		}
	}
	
	public void finish(V result) {
		finish(result, null);
	}
	
	@SuppressWarnings("unchecked")
	public void finish(V result, Throwable cause) {
		if (isDone())
			throw new IllegalStateException("Already complete");
		synchronized (this) {
			if (isDone())
				throw new IllegalStateException("Already complete");
			this.result = result;
			this.cause = cause;
			this.done = true;
			Object listener = this.listener;
			if (listener != null) {
				if (listener instanceof CopyOnWriteArraySet) {
					for (GenericFutureListener<Future<V>> l : (Iterable<GenericFutureListener<Future<V>>>)listener) {
						l.complete(this);
					}
				} else {
					((GenericFutureListener<Future<V>>) listener).complete(this);
				}
				this.listener = null;
			}
			notifyAll();
		}
	}

}
