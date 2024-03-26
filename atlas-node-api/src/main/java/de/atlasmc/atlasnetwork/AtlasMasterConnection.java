package de.atlasmc.atlasnetwork;

import de.atlasmc.atlasnetwork.AtlasNode.NodeStatus;
import de.atlasmc.server.NodeServer;

public interface AtlasMasterConnection {
	
	void updateNodeStatus(NodeStatus status);
	
	void registerServer(NodeServer server);
	
	void updateServer(NodeServer server);

}
