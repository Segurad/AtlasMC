package de.atlascore.inventory;

import de.atlasmc.entity.HumanEntity;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.PlayerInventory;

public class CorePlayerInventory extends CoreInventory implements PlayerInventory {

	private int heldSlot;
	
	public CorePlayerInventory(String title, InventoryHolder holder) {
		super(40, InventoryType.PLAYER, title, holder);
		if (holder == null) throw new IllegalArgumentException("InventoryHolder can not be null");
		if (holder instanceof HumanEntity) throw new IllegalArgumentException("InventoryHolder must be at least a HumanEntity");
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		if (slot < 0) return SlotType.OUTSIDE;		
		if (slot <= 8) return SlotType.QUICKBAR;
		if (slot <= 35) return SlotType.CONTAINER;
		if (slot <= 39) return SlotType.ARMOR;
		if (slot == 40) return SlotType.QUICKBAR;
		return SlotType.OUTSIDE;
	}

	@Override
	public HumanEntity getHolder() {
		return (HumanEntity) super.getHolder();
	}

	@Override
	public ItemStack getItemInMainHand() { 
		return getItem(heldSlot);
	}
	
	@Override
	public void setItemInMainHand(ItemStack item) {
		setItem(heldSlot, item);	
	}

	@Override
	public ItemStack getItemInOffHand() {
		return getItem(40);
	}
	
	@Override
	public void setItemInOffHand(ItemStack item) {
		setItem(40, item);	
	}

	@Override
	public int getHeldItemSlot() {
		return heldSlot;
	}

	@Override
	public void setHeldItemSlot(int slot) {
		if (slot > 8 || slot < 0) throw new IllegalArgumentException("Slot must be between 0 and 8");
		this.heldSlot = slot;
	}

	@Override
	public ItemStack getHelmet() {
		return getItem(39);
	}

	@Override
	public ItemStack getChestplate() {
		return getItem(38);
	}

	@Override
	public ItemStack getLeggings() {
		return getItem(37);
	}

	@Override
	public ItemStack getBoots() {
		return getItem(36);
	}

	@Override
	public void setHelmet(ItemStack item) {
		setItem(39, item);
	}

	@Override
	public void setChestplate(ItemStack item) {
		setItem(38, item);
	}

	@Override
	public void setLeggings(ItemStack item) {
		setItem(37, item);
	}

	@Override
	public void setBoots(ItemStack item) {
		setItem(36, item);
	}

	@Override
	public CraftingInventory getCraftingInventory() {
		// TODO Auto-generated method stub
		return null;
	}

}
