package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.AbstractServerEvent;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryEntityNBTEvent extends AbstractServerEvent {

	public PlayerQueryEntityNBTEvent(Player player, int transactionID, int entityID) {
		super(player.getServer());
	}

	@Override
	public ServerHandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
