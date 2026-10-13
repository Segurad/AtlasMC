package de.atlasmc.network.io;

import java.io.Closeable;
import java.security.cert.Certificate;
import java.util.Collection;

import de.atlasmc.datarepository.Repository;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.network.NetworkStats;
import de.atlasmc.network.NodeConfig;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.Future;

public interface MasterClient extends Closeable {

	@NotNull
	ConnectionHandler getConnection();
	
	@NotNull
	Certificate getCertificate();
	
	boolean isConnected();
	
	@NotNull
	NetworkInfo getNetworkInfo();
	
	@NotNull
	NetworkInfo getNetworkInfoMaintenance();
	
	@NotNull
	NetworkStats getNetworkStats();
	
	@NotNull
	Future<NodeConfig> getNodeConfig(String name);
	
	@NotNull
	Future<Collection<NodeConfig>> getNodeConfigs(Collection<String> names);
	
	@NotNull
	Future<SocketConfig> getSocketConfig(String name);
	
	@NotNull
	Future<Collection<SocketConfig>> getSocketConfigs(Collection<String> names);
	
	@NotNull
	Collection<Repository> getRepositories();
	
}
