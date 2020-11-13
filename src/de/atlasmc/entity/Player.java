package de.atlasmc.entity;

import de.atlasmc.server.AtlasServer;
import de.atlasmc.server.ServerPlayer;

public interface Player extends ServerPlayer {
	
	public AtlasServer getServer();

}
