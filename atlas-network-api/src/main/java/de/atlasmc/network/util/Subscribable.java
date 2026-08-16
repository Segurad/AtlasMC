package de.atlasmc.network.util;

import java.util.Collection;

public interface Subscribable<T> {
	
	boolean subscribe(Subscriber<? extends T> context);
	
	boolean unsubscribe(Subscriber<? extends T> context);
	
	Collection<Subscriber<? extends T>> getSubscriber();

	void clearSubscriber();
	
}
