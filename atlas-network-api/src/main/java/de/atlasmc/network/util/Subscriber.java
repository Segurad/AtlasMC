package de.atlasmc.network.util;

import de.atlasmc.util.annotation.NotNull;

public interface Subscriber<T> {
	
	/**
	 * Called by the subscription handler when the subscriber was added
	 * @param value
	 */
	void onSubscribe(@NotNull T value);
	
	/**
	 * Called by the subscription handler if a subscribed object was updated
	 * @param value
	 */
	void onUpdate(@NotNull T value);
	
	/**
	 * Called by the subscription handler when the subscriber was removed
	 * @param value
	 */
	void onUnsubscribe(@NotNull T value);

}
