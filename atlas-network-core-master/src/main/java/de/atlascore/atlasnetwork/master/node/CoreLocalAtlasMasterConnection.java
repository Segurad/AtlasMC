package de.atlascore.atlasnetwork.master.node;

import de.atlasmc.atlasnetwork.AtlasMasterConnection;
import de.atlasmc.atlasnetwork.AtlasNode.NodeStatus;
import de.atlasmc.server.NodeServer;

public class CoreLocalAtlasMasterConnection implements AtlasMasterConnection {
	
	private final CoreLocalAtlasNodeMaster node;
	
	public CoreLocalAtlasMasterConnection(CoreLocalAtlasNodeMaster node) {
		this.node = node;
	}

	@Override
	public void updateNodeStatus(NodeStatus status) {
		this.node.setStatus(status);
	}

	@Override
	public void registerServer(NodeServer server) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateServer(NodeServer server) {
		// TODO Auto-generated method stub
		
	}

}
