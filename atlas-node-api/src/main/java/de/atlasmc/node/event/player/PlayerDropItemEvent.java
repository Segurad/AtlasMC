package de.atlasmc.node.event.player;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.ItemStack;

public class PlayerDropItemEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private ItemStack drop;
	private int dropAmount;
	private int removeAmount;
	private final boolean all;
	private boolean cancelled;
	
	public PlayerDropItemEvent(Player player, ItemStack drop, int dropAmount, int removeAmount, boolean all) {
		super(player);
		this.drop = drop;
		this.dropAmount = dropAmount;
		this.removeAmount = removeAmount;
		this.all = all;
	}
	
	public ItemStack getDropedItem() {
		return drop;
	}
	
	public void setDropedItem(ItemStack drop) {
		this.drop = drop;
	}
	
	public int getDropAmount() {
		return dropAmount;
	}
	
	public void setDropAmount(int dropAmount) {
		this.dropAmount = dropAmount;
	}
	
	public int getRemoveAmount() {
		return removeAmount;
	}
	
	public void setRemoveAmount(int removeAmount) {
		this.removeAmount = removeAmount;
	}
	
	public boolean isDropAll() {
		return all;
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
