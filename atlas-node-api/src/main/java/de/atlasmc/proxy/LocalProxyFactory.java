package de.atlasmc.proxy;

import java.util.UUID;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/proxy")
public interface LocalProxyFactory {
	
	LocalProxy createProxy(UUID uuid, LocalAtlasNode node, int port, ProxyConfig config);

}
