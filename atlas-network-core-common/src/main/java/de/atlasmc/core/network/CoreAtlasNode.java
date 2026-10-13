package de.atlasmc.core.network;

import java.security.cert.Certificate;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.network.AtlasNode;

public abstract class CoreAtlasNode implements AtlasNode {
	
	private final UUID uuid;
	private final Certificate cert;
	private NodeStatus status;
	
	public CoreAtlasNode(UUID uuid, Certificate cert) {
		this.uuid = Objects.requireNonNull(uuid, "uuid");
		this.cert = Objects.requireNonNull(cert, "cert");
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
	public UUID getID() {
		return uuid;
	}
	
	@Override
	public Certificate getCertificate() {
		return cert;
	}
	
}
