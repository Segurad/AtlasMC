package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

/**
 * Called when a player changes his main hand side in his settings client side
 */
public class PlayerMainHandChangeEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final int mainHand;
	
	public PlayerMainHandChangeEvent(Player player, int mainHand) {
		super(player);
		this.mainHand = mainHand;
	}
	
	/**
	 * Returns the new main hand 0 = right, 1 = left
	 * @return main hand
	 */
	public int getMainHand() {
		return mainHand;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
