package de.atlascore.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;

public abstract class CoreAtlasNode implements AtlasNode {
	
	private final UUID uuid;
	private final PublicKey key;
	private NodeStatus status;
	
	public CoreAtlasNode(UUID uuid, PublicKey key) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.uuid = uuid;
		this.key = key;
		this.status = NodeStatus.OFFLINE;
	}

	@Override
	public NodeStatus getStatus() {
		return status;
	}
	
	public void setStatus(NodeStatus status) {
		if (status == null)
			throw new IllegalArgumentException("Status can not be null!");
		this.status = status;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}
	
	@Override
	public PublicKey getPublicKey() {
		return key;
	}
	
}
