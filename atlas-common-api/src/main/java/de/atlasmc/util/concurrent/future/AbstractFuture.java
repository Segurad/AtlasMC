package de.atlasmc.util.concurrent.future;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AbstractFuture<V> implements Future<V> {
	
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
		return done;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		if (isDone()) {
			return resultOrThrow();
		}
		synchronized (this) {
			if (isDone()) {
				return resultOrThrow();
			}
			wait();
			return resultOrThrow();
		}
	}
	
	private V resultOrThrow() throws ExecutionException {
		Throwable cause = this.cause;
		if (cause != null)
			throw new ExecutionException(cause);
		return result;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (isDone())
			return resultOrThrow();
		synchronized (this) {
			if (isDone())
				return resultOrThrow();
			int nanos = 0;
			if (unit.ordinal() < TimeUnit.MILLISECONDS.ordinal()) {
				long val = TimeUnit.NANOSECONDS.convert(nanos, unit);
				nanos = (int) (val % 1000000);
			};
			wait(TimeUnit.MILLISECONDS.convert(timeout, unit), nanos);
			return resultOrThrow();
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
		return done && result != null && cause == null && !isCancelled();
	}

	@Override
	public void setListener(FutureListener<V> listener) {
		if (isDone()) {
			listener.complete(this);
			return;
		}
		synchronized (this) {
			if (isDone()) {
				listener.complete(this);
				return;
			}
			Object currentListener = this.listener;
			if (currentListener == null) {
				this.listener = listener;
			} else if (currentListener instanceof CopyOnWriteArraySet) {
				@SuppressWarnings("unchecked")
				CopyOnWriteArraySet<Object> ary =  (CopyOnWriteArraySet<Object>) currentListener;
				ary.add(listener);
			} else {
				CopyOnWriteArraySet<Object> ary = new CopyOnWriteArraySet<>();
				ary.add(currentListener);
				ary.add(listener);
				this.listener = ary;
			}
		}
	}
	
	@Override
	public void removeListener(FutureListener<V> listener) {
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
	
	protected void finish(V result) {
		finish(result, null);
	}
	
	protected void finish(V result, Throwable cause) {
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
					@SuppressWarnings("unchecked")
					Iterable<FutureListener<V>> listeners = (Iterable<FutureListener<V>>) listener;
					for (FutureListener<V> l : listeners) {
						l.complete(this);
					}
				} else {
					@SuppressWarnings("unchecked")
					FutureListener<V> l = (FutureListener<V>) listener;
					l.complete(this);
				}
				this.listener = null;
			}
			notifyAll();
		}
	}

}
