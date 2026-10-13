package de.atlasmc.network.io;

import de.atlasmc.IDHolder;

public enum RequestAction implements IDHolder {

	/**
	 * Requests a object
	 */
	REQUEST(true),
	/**
	 * Requests and subscribes a object
	 */
	SUBSCRIBE(true),
	/**
	 * Unsubscribes a object
	 */
	UNSUBSCRIBE(false),
	/**
	 * Update of a subscribed 
	 */
	UPDATE(true),
	/**
	 * Update if a object should be deleted
	 */
	DELETED(false);
	
	private final boolean payload;
	
	private RequestAction(boolean payload) {
		this.payload = payload;
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	/**
	 * Whether the actions result may have a payload
	 * @return true if payload
	 */
	public boolean hasPayload() {
		return payload;
	}
	
}
