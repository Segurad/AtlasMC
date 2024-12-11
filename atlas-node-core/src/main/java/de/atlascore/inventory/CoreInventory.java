package de.atlascore.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.GUI;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSetContainerSlot;
import de.atlasmc.io.protocol.play.PacketOutSetContainerContents;
import de.atlasmc.io.protocol.play.PacketOutSetContainerProperty;
import de.atlasmc.util.iterator.ArrayIterator;

public class CoreInventory implements Inventory {

	protected final ItemStack[] contents;
	protected final int storageSize;
	protected final List<Player> viewers;
	private final int size;
	private int stateID;
	private InventoryHolder holder;
	private final InventoryType type;
	private Chat title;
	private GUI gui;
	private boolean autoUpdate;
	
	public CoreInventory(int size, InventoryType type, Chat title, InventoryHolder holder) {
		this(size, size, type, title, holder);
	}
	
	public CoreInventory(int size, int storageSize, InventoryType type, Chat title, InventoryHolder holder) {
		this.size = size;
		this.storageSize = storageSize;
		this.contents = new ItemStack[size];
		this.viewers = new ArrayList<>(1);
		this.holder = holder;
		this.title = title;
		this.type = type;
	}
	
	@Override
	public Iterator<ItemStack> iterator() {
		return new ArrayIterator<>(getContents(), true);
	}
	
	@Override
	public Iterator<ItemStack> iteratorUnsafe() {
		return new ArrayIterator<>(getContents(), false);
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		setItem(slot, item, true);
	}
	
	@Override
	public void setItem(int slot, ItemStack item, boolean animation) {
		if (contents[slot] == item)
			return;
		if (item != null)
			item = item.clone();
		contents[slot] = item;
		if (autoUpdate) {
			stateID++;
			updateSlot(slot, animation);
		}
	}
	
	@Override
	public void remove(Material material) {
		if (material == null)
			throw new IllegalArgumentException("Material can not be null!");
		for (int i = 0; i < size; i++) {
			ItemStack item = contents[i];
			if (item != null && item.getType() == material) {
				contents[i] = null;
				if (autoUpdate) {
					stateID++;
					updateSlot(i, false);
				}
			}
		}
	}
	
	@Override
	public void removeSimilar(ItemStack item, boolean ignoreAmount, boolean ignoreDamage) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		for (int i = 0; i < size; i++) {
			ItemStack slotitem = contents[i];
			if (item.isSimilar(slotitem, ignoreAmount, ignoreDamage)) {
				contents[i] = null;
				if (autoUpdate) {
					stateID++;
					updateSlot(i, false);
				}
			}
		}
	}
	
	@Override
	public void setItemUnsafe(int slot, ItemStack item, boolean animation) {
		if (contents[slot] == item)
			return;
		contents[slot] = item;
		if (autoUpdate) {
			stateID++;
			updateSlot(slot, animation);
		}
	}

	@Override
	public void setItemUnsafe(int slot, ItemStack item) {
		setItemUnsafe(slot, item, true);
	}
	
	@Override
	public int addItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		int restAmount = item.getAmount();
		if (restAmount <= 0)
			return 0;
		for (int i = 0; i < storageSize; i++) {
			ItemStack slotItem = contents[i];
			if (slotItem == null)
				continue;
			int missing = slotItem.getType().getMaxAmount()-slotItem.getAmount();
			if (missing <= 0)
				continue;
			if (!slotItem.isSimilar(slotItem, true, false))
				continue;
			if (restAmount <= missing) {
				ItemStack clone = slotItem.clone();
				clone.setAmount(clone.getAmount()+restAmount);
				setItem(i, clone, false);
				return 0;
			}
			restAmount-=missing;
			ItemStack clone = slotItem.clone();
			clone.setAmount(clone.getAmount()+missing);
			setItem(i, clone, false);
		}
		for (int i = 0; i < storageSize; i++) {
			if (contents[i] == null) {
				if (restAmount > item.getType().getMaxAmount()) {
					restAmount -= item.getType().getMaxAmount();
					setItem(i, item, false);
				} else {
					ItemStack clone = item.clone();
					clone.setAmount(restAmount);
					setItemUnsafe(i, clone, false);
					return 0;
				}
			}
		}
		return restAmount;
	}
	
	@Override
	public List<ItemStack> addItem(ItemStack... items) {
		List<ItemStack> left = null;
		for (int i = 0; i < items.length; i++) {
			ItemStack item = items[i];
			if (item == null)
				continue;
			int remain = addItem(item);
			if (remain == 0)
				continue;
			if (left == null)
				left = new ArrayList<>(items.length-i);
			ItemStack clone = item.clone();
			clone.setAmount(remain);
			left.add(clone);
		}
		return left != null ? left : List.of();
	}

	@Override
	public ItemStack getItem(int slot) {
		ItemStack item = contents[slot];
		return item != null ? item.clone() : null;
	}
	
	@Override
	public ItemStack getItemUnsafe(int slot) {
		return contents[slot];
	}

	@Override
	public List<Player> getViewers() {
		return viewers;
	}
	
	@Override
	public Chat getTitle() {
		return title;
	}

	@Override
	public SlotType getSlotType(int slot) {
		return SlotType.CONTAINER;
	}

	@Override
	public InventoryType getType() {
		return type;
	}

	@Override
	public InventoryHolder getHolder() {
		return holder;
	}

	@Override
	public void setHolder(InventoryHolder holder) {
		this.holder = holder;
	}

	@Override
	public ItemStack[] getContents() {
		ItemStack[] contents = new ItemStack[size];
		for (int i = 0; i < size; i++) {
			ItemStack item = this.contents[i];
			if (item != null)
				item = item.clone();
			contents[i] = item;
		}
		return contents;
	}
	
	@Override
	public ItemStack[] getContentsUnsafe() {
		return contents;
	}

	@Override
	public void setContentsUnsafe(ItemStack[] contents) {
		System.arraycopy(contents, 0, this.contents, 0, size);
		if (autoUpdate) {
			stateID++;
			updateSlots();
		}
	}

	@Override
	public void setContents(ItemStack[] contents) {
		if (contents == null)
			throw new IllegalArgumentException("Contents can not be null!");
		if (contents.length != size)
			throw new IllegalAccessError("Contents length must be the same as the size of this Inventory!");
		for (int i = 0; i < size; i++) {
			ItemStack item = contents[i];
			if (item != null)
				item = item.clone();
			this.contents[i] = item;
		}
		if (autoUpdate) {
			stateID++;
			updateSlots();
		}
	}

	@Override
	public boolean contains(Material material) {
		for (ItemStack item : contents) {
			if (item.getType() == material) return true;
		}
		return false;
	}

	@Override
	public int count(Material material) {
		if (material == null)
			throw new IllegalArgumentException("Material can not be null!");
		int c = 0;
		for (ItemStack item : contents) {
			if (item == null) 
				continue;
			if (item.getType() == material) 
				c += item.getAmount();
		}
		return c;
	}
	
	@Override
	public void updateSlot(int slot, boolean animation) {
		if (slot == -999) 
			return;
		for (Player player : viewers) {
			updateSlot(player, slot, animation);
		}
	}
	
	@Override
	public void updateSlot(Player player, int slot, boolean animation) {
		if (slot == -999) 
			return;
		ItemStack item = getItemUnsafe(slot);
		internalUpdateSlot(player, item, slot, animation);
	}
	
	/**
	 * Sends a slot update to the given player with a the given item
	 * @param player
	 * @param item
	 * @param slot
	 * @param animation
	 */
	protected void internalUpdateSlot(Player player, ItemStack item, int slot, boolean animation) {
		InventoryView view = player.getOpenInventory();
		int raw = view.convertSlot(this, slot);
		int id = view.getViewID();
		if (getType() == InventoryType.PLAYER) {
			if (slot > 35) {
				id = 0; // update armor or offhand slot
			} else if (slot < 9) {
				if (animation) {
					id = 0; // update hotbar with animation
				} else 
					id = -2; // update hotbar without animation
			} else 
				if (!animation) id = -2; // update slot without animation
		} else 
			if (!animation) id = -2;
		PlayerConnection con = player.getConnection();
		PacketOutSetContainerSlot packet = new PacketOutSetContainerSlot();
		packet.windowID = id;
		packet.slot = raw;
		packet.stateID = stateID;
		packet.item = item;
		con.sendPacked(packet);
	}

	@Override
	public void updateSlots() {
		ItemStack[] contents = getContentsUnsafe();
		for (Player player : viewers) {
			internalUpdateSlots(player, contents);
		}
	}

	@Override
	public void updateSlots(Player player) {
		internalUpdateSlots(player, getContentsUnsafe());
	}
	
	/**
	 * Sends a slot update to the given player with a the given items
	 * @param player
	 * @param items
	 */
	protected void internalUpdateSlots(Player player, ItemStack[] items) {
		InventoryView view = player.getOpenInventory();
		int id = view.getViewID();
		PlayerConnection con = player.getConnection();
		PacketOutSetContainerContents packet = new PacketOutSetContainerContents();
		packet.windowID = id;
		packet.stateID = stateID;
		packet.setItems(items);
		con.sendPacked(packet);
	}

	@Override
	public void removeItems(Material material, int count) {
		for (int i = 0; i < size; i++) {
			ItemStack item = contents[i];
			if (item == null) 
				continue;
			if (item.getType() != material) 
				return;
			if (count >= item.getAmount()) {
				count -= item.getAmount();
				contents[i] = null;
				continue;
			}
			item.setAmount(item.getAmount()-count);
			updateSlot(i, true);
			break;
		}
	}

	@Override
	public boolean hasViewers() {
		return !viewers.isEmpty();
	}
	
	/**
	 * Sends a {@link PacketOutSetContainerProperty} to all viewers
	 * @param property the property field
	 * @param value the property value
	 */
	protected void updateProperty(int property, int value) {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	protected void updateProperty(int property, int value, Player player) {
		int windowID = player.getOpenInventory().getViewID();
		PlayerConnection con = player.getConnection();
		PacketOutSetContainerProperty packet = new PacketOutSetContainerProperty();
		packet.windowID = windowID;
		packet.property = property;
		packet.value = value;
		con.sendPacked(packet);
	}


	@Override
	public void updateProperties() {}

	@Override
	public void updateProperties(Player player) {}

	@Override
	public void setTitle(Chat title) {
		this.title = title;
	}

	@Override
	public int countItems() {
		int count = 0;
		for (ItemStack item : contents) {
			if (item != null) count++;
		}
		return count;
	}
	
	@Override
	public void clear() {
		Arrays.fill(contents, null);	
		updateSlots();
	}

	@Override
	public GUI getGUI() {
		return gui;
	}

	@Override
	public void setGUI(GUI gui) {
		if (gui != null && gui.getInventory() != this)
			throw new IllegalArgumentException("Can only asigne gui with this inventory!");
		GUI oldGUI = this.gui;
		this.gui = gui;
		if (oldGUI != null)
			oldGUI.notifyRemoved();
	}

	@Override
	public int getStateID() {
		return stateID;
	}
	
	@Override
	public int updateStateID() {
		return ++stateID;
	}

	@Override
	public boolean setAutoUpdate(boolean update) {
		boolean old = this.autoUpdate;
		this.autoUpdate = update;
		return old;
	}

	@Override
	public boolean getAutoUpdate() {
		return autoUpdate;
	}

}
