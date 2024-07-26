package de.atlasmc.master.server;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key = "atlas-master:server/deployment_method")
public interface ServerDeploymentMethod {
	
	void deploy(ServerGroup group, int count);

}
