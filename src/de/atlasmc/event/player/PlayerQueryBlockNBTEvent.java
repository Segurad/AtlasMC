package de.atlasmc.event.player;

import de.atlasmc.Location;
import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryBlockNBTEvent extends PlayerEvent {

	public PlayerQueryBlockNBTEvent(Player player, int transactionID, Location loc) {
		super(player);
		// TODO
	}

	@Override
	public ServerHandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
