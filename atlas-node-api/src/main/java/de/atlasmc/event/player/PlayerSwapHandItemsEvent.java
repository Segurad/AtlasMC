package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.ItemStack;

public class PlayerSwapHandItemsEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private ItemStack mainHand, offHand;
	private boolean cancelled;
	
	public PlayerSwapHandItemsEvent(Player player, ItemStack mainHand, ItemStack offHand) {
		super(player);
		this.mainHand = mainHand;
		this.offHand = offHand;
	}
	
	public ItemStack getMainHand() {
		return mainHand;
	}
	
	public ItemStack getOffHand() {
		return offHand;
	}
	
	public void setMainHand(ItemStack mainHand) {
		this.mainHand = mainHand;
	}
	
	public void setOffHand(ItemStack offHand) {
		this.offHand = offHand;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
