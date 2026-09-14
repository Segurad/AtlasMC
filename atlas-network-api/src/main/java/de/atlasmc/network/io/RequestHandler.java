package de.atlasmc.network.io;

import de.atlasmc.io.codec.StreamCodec;

public interface RequestHandler<T> {
	
	Class<T> getType();
	
	/**
	 * Codec for used by this handler
	 * @return
	 */
	StreamCodec<T> getCodec();
	
	/**
	 * Handles the response of a request
	 * @param value
	 */
	void handle(T value);

}
