package de.atlasmc.plugin;

import java.util.Collection;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.util.Builder;

public interface NodeBuilder extends Builder<LocalAtlasNode> {

	ProtocolAdapter getDefaultProtocol();

	NodeBuilder setDefaultProtocol(ProtocolAdapter defaultProtocol);

	Collection<ProxyConfig> getProxyConfigs();
	
	NodeBuilder setServerManager(NodeServerManager serverManager);

}
