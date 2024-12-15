package de.atlasmc.event.player;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

/**
 * Called when a Player clicks a craftable Recipe in the RecipeBook
 */
public class PlayerQuickcraftRequestEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final NamespacedKey recipeID;
	
	private boolean craftAll;
	private boolean cancelled;
	
	public PlayerQuickcraftRequestEvent(Player player, NamespacedKey recipe, boolean craftAll) {
		super(player);
		this.recipeID = recipe;
		this.craftAll = craftAll;
	}
	
	public NamespacedKey getRecipeID() {
		return recipeID;
	}
	
	public boolean isCraftAll() {
		return craftAll;
	}
	
	public void setCraftAll(boolean craftAll) {
		this.craftAll = craftAll;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}
	
}
