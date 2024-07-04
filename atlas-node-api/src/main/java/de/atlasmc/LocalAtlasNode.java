package de.atlasmc;

import java.io.File;
import java.security.KeyPair;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.NodePlayer;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;

public interface LocalAtlasNode extends AtlasNode, SyncThreadHolder {
	
	NodeServerManager getServerManager();
	
	Collection<NodeServer> getServers();

	Collection<LocalProxy> getProxies();

	ProtocolAdapterHandler getProtocolAdapterHandler();

	Scheduler getScheduler();

	Log getLogger();

	File getWorkdir();
	
	PluginManager getPluginManager();
	
	KeyPair getKeyPair();
	
	DataRepositoryHandler getDataHandler();

	void registerProxy(LocalProxy proxy);
	
	NodeServer getLocalServer(UUID uuid);
	
	LocalProxy getLocalProxy(UUID uuid);

	NodePlayer getLocalPlayer(String name);
	
	NodePlayer getLocalPlayer(UUID name);
	
	void setStatus(NodeStatus status);

}
