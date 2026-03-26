package de.atlasmc.node.event.player;

import java.util.Objects;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.MainHand;

/**
 * Called when a player changes his main hand side in his settings client side
 */
public class PlayerMainHandChangeEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final MainHand mainHand;
	
	public PlayerMainHandChangeEvent(Player player, MainHand mainHand) {
		super(player);
		this.mainHand = Objects.requireNonNull(mainHand);
	}
	
	public MainHand getMainHand() {
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
