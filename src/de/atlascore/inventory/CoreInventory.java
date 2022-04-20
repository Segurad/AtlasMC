package de.atlascore.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;
import de.atlasmc.io.protocol.play.PacketOutWindowItems;
import de.atlasmc.io.protocol.play.PacketOutWindowProperty;
import de.atlasmc.util.ArrayIterator;

public class CoreInventory implements Inventory {

	protected final ItemStack[] contents;
	protected final List<Player> viewers;
	private final int size;
	private InventoryHolder holder;
	private final InventoryType type;
	private Chat title;
	
	public CoreInventory(int size, InventoryType type, Chat title, InventoryHolder holder) {
		this.size = size;
		this.contents = new ItemStack[size];
		this.viewers = new ArrayList<Player>(1);
		this.holder = holder;
		this.title = title;
		this.type = type;
	}
	
	@Override
	public Iterator<ItemStack> iterator() {
		return new ArrayIterator<>(contents, true);
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
		contents[slot] = item;
		updateSlot(slot, animation);
	}

	@Override
	public ItemStack getItem(int slot) {
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
		return contents;
	}

	@Override
	public void setContents(ItemStack[] contents) {
		final int newSize = contents.length;
		final int oldSize = this.contents.length;
		for (int i = 0; i < newSize && i < oldSize; i++) {
			this.contents[i] = contents[i];
		}
		updateSlots();
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
		int c = 0;
		for (ItemStack item : contents) {
			if (item == null) continue;
			if (item.getType() == material) c += item.getAmount();
		}
		return c;
	}
	
	@Override
	public void updateSlot(int slot, boolean animation) {
		for (Player player : viewers) {
			updateSlot(player, slot, animation);
		}
	}
	
	@Override
	public void updateSlot(Player player, int slot, boolean animation) {
		if (slot == -999) return;
		InventoryView view = player.getOpenInventory();
		int raw = view.convertSlot(this, slot);
		int id = view.getViewID();
		if (getType() == InventoryType.PLAYER) {
			if (slot > 35) {
				id = 0; // update armor or offhand slot
			} else if (slot < 9) {
				if (animation) {
					id = 0; // update hotbar with animation
				} else id = -2; // update hotbar without animation
			} else if (!animation) id = -2; // update slot without animation
		} else if (!animation) id = -2;
		PlayerConnection con = player.getConnection();
		PacketOutSetSlot packet = con.getProtocol().createPacket(PacketOutSetSlot.class);
		packet.setWindowID(id);
		packet.setSlot(raw);
		packet.setItem(getItem(slot));
		con.sendPacked(packet);
	}


	@Override
	public void updateSlots() {
		for (Player player : viewers) {
			updateSlots(player);
		}
	}

	@Override
	public void updateSlots(Player player) {
		InventoryView view = player.getOpenInventory();
		int id = view.getViewID();
		ItemStack[] items = null;
		if (view.getTopInventory() == this) {
			items = getContents();
		}
		PlayerConnection con = player.getConnection();
		PacketOutWindowItems packet = con.getProtocol().createPacket(PacketOutWindowItems.class);
		packet.setWindowID(id);
		packet.setSlots(items);
		con.sendPacked(packet);
	}

	@Override
	public void removeItems(Material material, int count) {
		for (int i = 0; i < size; i++) {
			ItemStack item = contents[i];
			if (item == null) continue;
			if (item.getType() != material) return;
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
	 * Sends a {@link PacketOutWindowProperty} to all viewers
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
		PacketOutWindowProperty packet = con.getProtocol().createPacket(PacketOutWindowProperty.class);
		packet.setWindowID(windowID);
		packet.setProperty(property);
		packet.setValue(value);
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

}
