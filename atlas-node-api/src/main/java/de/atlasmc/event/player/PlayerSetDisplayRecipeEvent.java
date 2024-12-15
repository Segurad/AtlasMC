package de.atlasmc.event.player;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class PlayerSetDisplayRecipeEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final NamespacedKey recipe;
	
	public PlayerSetDisplayRecipeEvent(Player player, NamespacedKey recipe) {
		super(player);
		this.recipe = recipe;
	}
	
	public NamespacedKey getRecipe() {
		return recipe;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
