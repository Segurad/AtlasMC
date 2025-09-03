package de.atlasmc;

import java.util.UUID;

import de.atlasmc.atlasnetwork.player.AtlasPlayer;
import de.atlasmc.chat.Messageable;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.server.NodeServer;
import de.atlasmc.socket.NodeSocket;

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
