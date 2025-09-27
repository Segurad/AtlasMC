package de.atlasmc.core.master.server;

import java.util.Collection;

import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.node.AtlasNode;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.server.ServerDeploymentMethod;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry = "atlas-master:server/deployment_method", key = "atlas-core:simple_deployment")
public class CoreSimpleServerDeploymentMethod implements ServerDeploymentMethod {

	@Override
	public void deploy(ServerGroup group, int count) {
		group.setTimeout(20);
		if (count <= 0)
			return;
		NodeManager nodeManager = AtlasMaster.getNodeManager();
		Collection<AtlasNode> nodes = nodeManager.getNodes();
		if (nodes.isEmpty())
			return;
		long threshold = group.getMemoryThreshold();
		// TODO float utilisation = group.getMemoryUtilisation();
		long requiredMemory = threshold * 1000L * 1000L;
		for (AtlasNode node : nodes) {
			if (group.isInternal()) {
				long maxMemory = node.getMaxHeapSize();
				long memory = node.getUsedHeap();				
				if (maxMemory-memory < requiredMemory)
					continue;
			}
			node.deployServer(group);
		}
	}

}
