package de.atlasmc.network.io;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.annotation.NotNull;

public interface RequestHandler<T> {
	
	@NotNull
	Class<T> getType();
	
	/**
	 * Codec for used by this handler
	 * @return codec
	 */
	@NotNull
	StreamCodec<T> getCodec();
	
	/**
	 * Handles the response of a request
	 * @param value
	 */
	void handle(T value);

}
