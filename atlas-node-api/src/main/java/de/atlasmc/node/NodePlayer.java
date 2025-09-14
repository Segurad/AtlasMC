package de.atlasmc.node;

import java.util.UUID;

import de.atlasmc.chat.Messageable;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.server.NodeServer;

public interface NodePlayer extends Messageable {
	
	String getInternalName();
	
	UUID getInternalUUID();
	
	String getMojangName();
	
	UUID getMojangUUID();
	
	PlayerConnection getConnection();
	
	Player getPlayer();
	
	AtlasPlayer getAtlasPlayer();
	
	NodeSocket getProxy();
	
	NodeServer getLocalServer();

}
