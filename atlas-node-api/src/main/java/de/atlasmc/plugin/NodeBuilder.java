package de.atlasmc.plugin;

import java.security.KeyPair;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.factory.ChatFactory;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.proxy.ProxyConfig;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.util.Builder;
import de.atlasmc.util.TickingThread;

public interface NodeBuilder extends Builder<LocalAtlasNode> {

	NodeBuilder setMainThread(TickingThread mainThread);

	PluginManager getPluginManager();

	NodeBuilder setPluginManager(PluginManager pluginManager);

	Scheduler getScheduler();

	NodeBuilder setScheduler(Scheduler scheduler);

	ProtocolAdapter getDefaultProtocol();

	NodeBuilder setDefaultProtocol(ProtocolAdapter defaultProtocol);

	KeyPair getKeyPair();

	Collection<ProxyConfig> getProxyConfigs();

	NodeBuilder setChatFactory(ChatFactory chatFactory);

	ChatFactory getChatFactory();

	TickingThread getMainThread();

	NodeBuilder setDataHandler(DataRepositoryHandler dataHandler);

	DataRepositoryHandler getDataHandler();
	
	NodeBuilder setServerManager(NodeServerManager serverManager);
	
	UUID getUUID();

}
