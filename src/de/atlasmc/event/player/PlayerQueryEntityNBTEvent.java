package de.atlasmc.event.player;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.entity.Player;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryEntityNBTEvent extends GenericEvent<LocalServer, ServerHandlerList>{

	public PlayerQueryEntityNBTEvent(Player player, int transactionID, int entityID) {
		super(player.getServer());
	}

	@Override
	public ServerHandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
