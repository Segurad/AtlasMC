package de.atlasmc.node.io.protocol.cookie;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.factory.Factory;
import io.netty.buffer.ByteBuf;

@RegistryHolder(key = "atlas:factory/cookie_factory", target = Target.INSTANCE)
public interface CookieFactory extends Factory {
	
	static final RegistryKey<CookieFactory> REGISTRY_KEY = Registries.getRegistryKey(CookieFactory.class);

	default Cookie createCookie(NamespacedKey key) {
		return createCookie(key, null);
	}
	
	Cookie createCookie(NamespacedKey key, ByteBuf payload);
	
}
