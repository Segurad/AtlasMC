package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryEntityNBTEvent extends PlayerEvent {

	public PlayerQueryEntityNBTEvent(Player player, int transactionID, int entityID) {
		super(player);
	}

	@Override
	public ServerHandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
