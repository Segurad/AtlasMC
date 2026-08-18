package de.atlasmc.node.io.protocol.cookie;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.RegistryValue;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry = "atlas:factory/cookie_factory", key = "atlas:raw_cookie", isDefault = true)
public class RawCookieFactory implements CookieFactory {

	@Override
	public Cookie createCookie(NamespacedKey key, ByteBuf payload) {
		var cookie = new RawCookie(key);
		cookie.setPayload(payload);
		return cookie;
	}

}
