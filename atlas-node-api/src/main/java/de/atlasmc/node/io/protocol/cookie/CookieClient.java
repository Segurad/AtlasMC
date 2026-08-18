package de.atlasmc.node.io.protocol.cookie;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

/**
 * Representing a client that handles requests from a {@link CookieManager}
 */
public interface CookieClient {

	void requestCookie(NamespacedKey key);
	
	void updateCookie(NamespacedKey key, ByteBuf payload);
	
}
