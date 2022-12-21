package de.atlascore.inventory;

import java.util.ArrayList;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutEntityEquipment;
import de.atlasmc.util.Pair;

public class CorePlayerInventory extends CoreInventory implements PlayerInventory {

	private static final int
	SLOT_HEAD = 39,
	SLOT_CHEST = 38,
	SLOT_LEGS = 37,
	SLOT_FEET = 36,
	SLOT_OFF_HAND = 40;
	
	private int heldSlot;
	
	public CorePlayerInventory(Chat title, InventoryHolder holder) {
		super(40, InventoryType.PLAYER, title, holder);
		if (holder == null) 
			throw new IllegalArgumentException("InventoryHolder can not be null");
		if (!(holder instanceof HumanEntity)) 
				throw new IllegalArgumentException("InventoryHolder must be at least a HumanEntity");
	}
	
	@Override
	public void setHolder(InventoryHolder holder) {
		throw new RuntimeException("Can not set holder for PlayerInventory!");
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
		return getItem(SLOT_OFF_HAND);
	}
	
	@Override
	public void setItemInOffHand(ItemStack item) {
		setItem(SLOT_OFF_HAND, item);	
	}

	@Override
	public int getHeldItemSlot() {
		return heldSlot;
	}

	@Override
	public void setHeldItemSlot(int slot) {
		if (slot > 8 || slot < 0) 
			throw new IllegalArgumentException("Slot must be between 0 and 8");
		this.heldSlot = slot;
		updateEquipmentSlot(EquipmentSlot.HAND, slot);
	}

	@Override
	public ItemStack getHelmet() {
		return getItem(SLOT_HEAD);
	}

	@Override
	public ItemStack getChestplate() {
		return getItem(SLOT_CHEST);
	}

	@Override
	public ItemStack getLeggings() {
		return getItem(SLOT_LEGS);
	}

	@Override
	public ItemStack getBoots() {
		return getItem(SLOT_FEET);
	}

	@Override
	public void setHelmet(ItemStack item) {
		setItem(SLOT_HEAD, item);
	}

	@Override
	public void setChestplate(ItemStack item) {
		setItem(SLOT_CHEST, item);
	}

	@Override
	public void setLeggings(ItemStack item) {
		setItem(SLOT_LEGS, item);
	}

	@Override
	public void setBoots(ItemStack item) {
		setItem(SLOT_FEET, item);
	}
	
	@Override
	public void setItem(int slot, ItemStack item, boolean animation) {
		super.setItem(slot, item, animation);
		if (slot == heldSlot) {
			updateEquipmentSlot(EquipmentSlot.HAND, slot);
			return;
		}
		switch (slot) {
		case SLOT_HEAD:
			updateEquipmentSlot(EquipmentSlot.HEAD, slot);
			break;
		case SLOT_CHEST:
			updateEquipmentSlot(EquipmentSlot.CHEST, slot);
			break;
		case SLOT_LEGS:
			updateEquipmentSlot(EquipmentSlot.LEGS, slot);
			break;
		case SLOT_FEET:
			updateEquipmentSlot(EquipmentSlot.FEET, slot);
			break;
		case SLOT_OFF_HAND:
			updateEquipmentSlot(EquipmentSlot.OFF_HAND, slot);
			break;
		}
	}

	@Override
	public CraftingInventory getCraftingInventory() {
		return getHolder().getCraftingInventory();
	}
	
	private void updateEquipmentSlot(EquipmentSlot equipmentSlot, int slot) {
		ItemStack item = getItem(slot).clone();
		ArrayList<Pair<EquipmentSlot, ItemStack>> slots = new ArrayList<>(1);
		slots.add(new Pair<>(equipmentSlot, item));
		for (Player viewer : getHolder().getViewers()) {
			PlayerConnection con = viewer.getConnection();
			PacketOutEntityEquipment packet = new PacketOutEntityEquipment();
			packet.setSlots(slots);
			con.sendPacked(packet);
		}
	}

}
