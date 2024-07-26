package de.atlasmc.master.server;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;

public interface ServerManager {

	ServerGroup getFallBack();

	ServerGroup getServerGroup(String name);

	Collection<ServerGroup> getServerGroups(String... name);

	Server getServer(UUID uuid);

	void addDeploymentMethod(NamespacedKey key, ServerDeploymentMethod method);

}
