package de.atlasmc.event.inventory;

import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.potion.PotionEffectType;

public class BeaconEffectChangeEvent extends InventoryEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private PotionEffectType newPrimary;
	private PotionEffectType newSecondary;
	private boolean cancelled;
	
	public BeaconEffectChangeEvent(InventoryView view, PotionEffectType newPrimary, PotionEffectType newSecondary) {
		super(view);
		if (view.getType() != InventoryType.BEACON) throw new IllegalArgumentException("Inventory must have the Type BEACON: " + view.getType());
		this.newPrimary = newPrimary;
		this.newSecondary = newSecondary;
	}
	
	@Override
	public BeaconInventory getInventory() {
		return (BeaconInventory) super.getInventory();
	}
	
	public PotionEffectType getNewPrimaryEffect() {
		return newPrimary;
	}
	
	public PotionEffectType getNewSecondaryEffect() {
		return newSecondary;
	}
	
	public void setNewPrimaryEffect(PotionEffectType effect) {
		this.newPrimary = effect;
	}
	
	public void setNewSecondaryEffect(PotionEffectType effect) {
		this.newSecondary = effect;
	}
	
	public PotionEffectType getPrimaryEffect() {
		return getInventory().getPrimaryEffectType();
	}
	
	public PotionEffectType getSecondaryEffect() {
		return getInventory().getSecondaryEffectType();
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
