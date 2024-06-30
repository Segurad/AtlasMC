package de.atlasmc.event.inventory;

import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.potion.PotionEffect;

public class BeaconEffectChangeEvent extends InventoryEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private PotionEffect newPrimary;
	private PotionEffect newSecondary;
	private boolean cancelled;
	private int newPrimaryID;
	private int newSecondaryID;
	
	public BeaconEffectChangeEvent(InventoryView view, PotionEffect newPrimary, PotionEffect newSecondary, int primaryID, int secondaryID) {
		super(view);
		if (view.getType() != InventoryType.BEACON) throw new IllegalArgumentException("Inventory must have the Type BEACON: " + view.getType());
		this.newPrimary = newPrimary;
		this.newSecondary = newSecondary;
		this.newPrimaryID = primaryID;
		this.newSecondaryID = secondaryID;
	}
	
	@Override
	public BeaconInventory getInventory() {
		return (BeaconInventory) super.getInventory();
	}
	
	public PotionEffect getNewPrimary() {
		return newPrimary;
	}
	
	public PotionEffect getNewSecondary() {
		return newSecondary;
	}
	
	public void setNewPrimaryEffect(PotionEffect effect) {
		this.newPrimary = effect;
	}
	
	public void setNewSecondaryEffect(PotionEffect effect) {
		this.newSecondary = effect;
	}
	
	public int getNewPrimaryID() {
		return newPrimaryID;
	}
	
	public int getNewSecondaryID() {
		return newSecondaryID;
	}
	
	public void setNewPrimaryID(int newPrimaryID) {
		this.newPrimaryID = newPrimaryID;
	}
	
	public void setNewSecondaryID(int newSecondaryID) {
		this.newSecondaryID = newSecondaryID;
	}
	
	public PotionEffect getPrimaryEffect() {
		return getInventory().getPrimaryEffect();
	}
	
	public PotionEffect getSecondaryEffect() {
		return getInventory().getSecondaryEffect();
	}
	
	public int getPrimaryID() {
		return getInventory().getPrimaryID();
	}
	
	public int getSecondaryID() {
		return getInventory().getSecondaryID();
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
