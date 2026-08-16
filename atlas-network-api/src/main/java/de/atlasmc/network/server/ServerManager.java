package de.atlasmc.network.server;

import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

public interface ServerManager {
	
	ServerGroup getFallBack();
	
	@Nullable
	ServerGroup getServerGroup(String name);
	
	@Nullable
	BaseServer getServer(UUID uuid);
	
	@NotNull
	Future<ServerGroup> loadServerGroup(String name);
	
	@NotNull
	Future<BaseServer> loadServer(UUID uuid);

}
