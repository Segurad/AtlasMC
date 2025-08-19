package de.atlascore.inventory;

import java.util.ArrayList;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSetEquipment;
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
		super(41, 36, InventoryType.PLAYER, title, holder);
		if (holder == null) 
			throw new IllegalArgumentException("InventoryHolder can not be null");
		if (!(holder instanceof HumanEntity)) 
				throw new IllegalArgumentException("InventoryHolder must be at least a HumanEntity");
	}
	
	@Override
	public void setHolder(InventoryHolder holder) {
		throw new IllegalStateException("Can not set holder for PlayerInventory!");
	}
	
	@Override
	public SlotType getSlotType(int slot) {
		if (slot < 0) 
			return SlotType.OUTSIDE;		
		if (slot <= 8) 
			return SlotType.QUICKBAR;
		if (slot <= 35) 
			return SlotType.CONTAINER;
		if (slot <= 39) 
			return SlotType.ARMOR;
		if (slot == 40) 
			return SlotType.QUICKBAR;
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
	public ItemStack getItemInMainHandUnsafe() { 
		return getItemUnsafe(heldSlot);
	}
	
	@Override
	public void setItemInMainHandUnsafe(ItemStack item) {
		setItemUnsafe(heldSlot, item);	
	}

	@Override
	public ItemStack getItemInOffHandUnsafe() {
		return getItemUnsafe(SLOT_OFF_HAND);
	}
	
	@Override
	public void setItemInOffHandUnsafe(ItemStack item) {
		setItemUnsafe(SLOT_OFF_HAND, item);	
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
		updateEquipmentSlot(slot);
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
	public ItemStack getHelmetUnsafe() {
		return getItem(SLOT_HEAD);
	}

	@Override
	public ItemStack getChestplateUnsafe() {
		return getItem(SLOT_CHEST);
	}

	@Override
	public ItemStack getLeggingsUnsafe() {
		return getItem(SLOT_LEGS);
	}

	@Override
	public ItemStack getBootsUnsafe() {
		return getItem(SLOT_FEET);
	}

	@Override
	public void setHelmetUnsafe(ItemStack item) {
		setItem(SLOT_HEAD, item);
	}

	@Override
	public void setChestplateUnsafe(ItemStack item) {
		setItem(SLOT_CHEST, item);
	}

	@Override
	public void setLeggingsUnsafe(ItemStack item) {
		setItem(SLOT_LEGS, item);
	}

	@Override
	public void setBootsUnsafe(ItemStack item) {
		setItem(SLOT_FEET, item);
	}

	@Override
	public CraftingInventory getCraftingInventory() {
		return getHolder().getCraftingInventory();
	}
	
	@Override
	public void updateSlot(int slot, boolean animation) {
		super.updateSlot(slot, animation);
		updateEquipmentSlot(slot);
	}
	
	private void updateEquipmentSlot(int slot) {
		EquipmentSlot equipmentSlot = null;
		if (slot == heldSlot) {
			equipmentSlot = EquipmentSlot.MAIN_HAND;
		} else {
			switch (slot) {
			case SLOT_HEAD:
				equipmentSlot = EquipmentSlot.HEAD;
				break;
			case SLOT_CHEST:
				equipmentSlot = EquipmentSlot.CHEST;
				break;
			case SLOT_LEGS:
				equipmentSlot = EquipmentSlot.LEGS;
				break;
			case SLOT_FEET:
				equipmentSlot = EquipmentSlot.FEET;
				break;
			case SLOT_OFF_HAND:
				equipmentSlot = EquipmentSlot.OFF_HAND;
				break;
			default:
				return;
			}
		}
		ItemStack item = getItemUnsafe(slot);
		ArrayList<Pair<EquipmentSlot, ItemStack>> slots = new ArrayList<>(1);
		slots.add(Pair.of(equipmentSlot, item));
		PacketOutSetEquipment packet = new PacketOutSetEquipment();
		packet.slots = slots;
		packet.entityID = getHolder().getID();
		for (Player viewer : getHolder().getViewers()) {
			PlayerConnection con = viewer.getConnection();
			con.sendPacked(packet);
		}
	}

	@Override
	public ItemStack[] getArmorContents() {
		ItemStack[] contents = new ItemStack[4];
		contents[0] = getItem(SLOT_FEET);
		contents[1] = getItem(SLOT_LEGS);
		contents[2] = getItem(SLOT_CHEST);
		contents[3] = getItem(SLOT_HEAD);
		return contents;
	}
	
	@Override
	public ItemStack[] getArmorContentsUnsafe() {
		ItemStack[] contents = new ItemStack[4];
		contents[0] = this.contents[SLOT_FEET];
		contents[1] = this.contents[SLOT_LEGS];
		contents[2] = this.contents[SLOT_CHEST];
		contents[3] = this.contents[SLOT_HEAD];
		return contents;
	}

	@Override
	public void setArmorContents(ItemStack[] items) {
		internalSetArmorContents(items, false);
	}
	
	@Override
	public void setArmorContentsUnsafe(ItemStack[] items) {
		internalSetArmorContents(items, true);
	}
	
	private void internalSetArmorContents(ItemStack[] items, boolean unsafe) {
		if (items == null)
			throw new IllegalArgumentException("Contents can not be null!");
		if (items.length != 4)
			throw new IllegalArgumentException("Contents must have a length of 4: " + items.length);
		if (!unsafe) {
			for (int i = 0; i < items.length; i++) {
				ItemStack item = items[i];
				if (item != null)
					items[i] = item.clone();
			}
		}
		ArrayList<Pair<EquipmentSlot, ItemStack>> slots = new ArrayList<>(4);
		if (items[0] != null) {
			contents[SLOT_FEET] = items[0];
			updateSlot(SLOT_FEET, false);
			slots.add(Pair.of(EquipmentSlot.FEET, items[0]));
		}
		if (items[1] != null) {
			contents[SLOT_LEGS] = items[1];
			updateSlot(SLOT_LEGS, false);
			slots.add(Pair.of(EquipmentSlot.LEGS, items[1]));
		}
		if (items[2] != null) {
			contents[SLOT_CHEST] = items[2];
			updateSlot(SLOT_CHEST, false);
			slots.add(Pair.of(EquipmentSlot.CHEST, items[2]));
		}
		if (items[3] != null) {
			contents[SLOT_HEAD] = items[3];
			updateSlot(SLOT_HEAD, false);
			slots.add(Pair.of(EquipmentSlot.HEAD, items[3]));
		}
		PacketOutSetEquipment packet = new PacketOutSetEquipment();
		packet.slots = slots;
		packet.entityID = getHolder().getID();
		for (Player viewer : getHolder().getViewers()) {
			PlayerConnection con = viewer.getConnection();
			con.sendPacked(packet);
		}
	}

}
