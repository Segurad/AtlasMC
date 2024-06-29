package de.atlascore.atlasnetwork.master.server;

import java.util.Collection;

import de.atlascore.atlasnetwork.master.node.CoreAtlasNodeMaster;
import de.atlascore.atlasnetwork.master.node.CoreNodeManager;

public class CoreSimpleServerDeploymentMethod implements CoreServerDeploymentMethod {
	
	private CoreNodeManager nodeManager;
	
	public CoreSimpleServerDeploymentMethod(CoreNodeManager nodeManager) {
		this.nodeManager = nodeManager;
	}

	@Override
	public void deploy(CoreServerGroup group, int count) {
		group.setTimeout(20);
		if (count <= 0)
			return;
		Collection<CoreAtlasNodeMaster> nodes = nodeManager.getNodes();
		if (nodes.isEmpty())
			return;
		int threshold = group.getMemoryThreshold();
		// TODO float utilisation = group.getMemoryUtilisation();
		long requiredMemory = threshold * 1000L * 1000L;
		for (CoreAtlasNodeMaster node : nodes) {
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
