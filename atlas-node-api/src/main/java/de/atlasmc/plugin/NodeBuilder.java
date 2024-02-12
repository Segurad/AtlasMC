package de.atlasmc.plugin;

import java.security.KeyPair;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.factory.ChatFactory;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.TickingThread;

public interface NodeBuilder {

	void setMainThread(TickingThread mainThread);

	PluginManager getPluginManager();

	void setPluginManager(PluginManager pluginManager);

	Scheduler getScheduler();

	void setScheduler(Scheduler scheduler);

	ProtocolAdapter getDefaultProtocol();

	void setDefaultProtocol(ProtocolAdapter defaultProtocol);

	KeyPair getKeyPair();

	Collection<ProxyConfig> getProxyConfigs();

	void setChatFactory(ChatFactory chatFactory);

	ChatFactory getChatFactory();

	TickingThread getMainThread();

	void setDataHandler(DataRepositoryHandler dataHandler);

	DataRepositoryHandler getDataHandler();
	
	UUID getUUID();

}
