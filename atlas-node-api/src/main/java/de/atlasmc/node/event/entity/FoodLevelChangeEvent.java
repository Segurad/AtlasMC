package de.atlasmc.node.event.entity;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.ItemStack;

public class FoodLevelChangeEvent extends EntityEvent implements Cancellable {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private ItemStack item;
	private int newLevel;
	private boolean cancelled;
	
	public FoodLevelChangeEvent(Entity entity, int newLevel, ItemStack item) {
		super(entity);
		this.newLevel = newLevel;
		this.item = item;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setFoodLevel(int level) {
		this.newLevel = level;
	}
	
	public int getFoodLevel() {
		return newLevel;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
