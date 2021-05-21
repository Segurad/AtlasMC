package de.atlasmc.event.player;

import de.atlasmc.Location;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.entity.Player;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryBlockNBTEvent extends GenericEvent<LocalServer, ServerHandlerList>{

	public PlayerQueryBlockNBTEvent(Player player, int transactionID, Location loc) {
		super(player.getCurrentServer());
		// TODO
	}

	@Override
	public ServerHandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
