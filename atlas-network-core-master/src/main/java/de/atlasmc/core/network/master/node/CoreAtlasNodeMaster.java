package de.atlasmc.core.network.master.node;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.core.network.CoreAtlasNode;
import de.atlasmc.network.AtlasNode;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.tick.Tickable;

public abstract class CoreAtlasNodeMaster extends CoreAtlasNode implements AtlasNode, Tickable {
	
	protected long maxHeap;
	protected long usedHeap;
	
	public CoreAtlasNodeMaster(UUID uuid, PublicKey key) {
		super(uuid, key);
	}

	public long getMaxHeapSize() {
		return maxHeap;
	}

	public long getUsedHeap() {
		return usedHeap;
	}

	public abstract void deployServer(ServerGroup group);
	
}
