package de.atlascore.proxy;

import java.util.UUID;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.proxy.LocalProxyFactory;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/proxy", key="atlas-core:factory/local_proxy_factory", isDefault = true)
public class CoreLocalProxyFactory implements LocalProxyFactory {

	@Override
	public LocalProxy createProxy(UUID uuid, LocalAtlasNode node, int port, ProxyConfig config) {
		return new CoreLocalProxy(uuid, node, port, config);
	}

}
