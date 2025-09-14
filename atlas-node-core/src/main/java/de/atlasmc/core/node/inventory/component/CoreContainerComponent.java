package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.readSlot;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ContainerComponent;
import io.netty.buffer.ByteBuf;

public class CoreContainerComponent extends AbstractItemComponent implements ContainerComponent {
	
	private List<ItemStack> slots;
	
	public CoreContainerComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreContainerComponent clone() {
		CoreContainerComponent clone = (CoreContainerComponent) super.clone();
		if (hasItems()) {
			clone.slots = new ArrayList<>();
			final int size = slots.size();
			for (int i = 0; i < size; i++) {
				ItemStack item = slots.get(i);
				clone.slots.add(item);
			}
		}
		return clone;
	}

	@Override
	public List<ItemStack> getItems() {
		if (slots == null)
			slots = new ArrayList<>();
		return slots;
	}

	@Override
	public boolean hasItems() {
		return slots != null && !slots.isEmpty();
	}

	@Override
	public void setItem(int slot, ItemStack item) {
		if (item == null && slots != null) {
			slots.remove(slot);
			return;
		}
		slots.set(slot, item);
	}
	
	@Override
	public void addItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getItems().add(item);
	}

	@Override
	public void removeItem(ItemStack item) {
		if (!hasItems())
			return;
		slots.remove(item);
	}

	@Override
	public void removeItem(ItemType type) {
		if (!hasItems())
			return;
		for (int i = 0; i < slots.size(); i++) {
			ItemStack item = slots.get(i);
			if (item.getType() != type)
				continue;
			slots.remove(i);
			break;
		}
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int count = readVarInt(buf);
		for (int i = 0; i < count; i++) {
			ItemStack item = readSlot(buf);
			if (item == null)
				continue;
			addItem(item);
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (!hasItems()) {
			writeVarInt(0, buf);
			return;
		}
		final int size = slots.size();
		writeVarInt(size, buf);
		for (int i = 0; i < size; i++) {
			ItemStack item = slots.get(i);
			writeSlot(item, buf);
		}
	}

}
