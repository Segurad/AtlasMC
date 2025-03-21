package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.protocol.ProtocolUtil.readSlot;
import static de.atlasmc.io.protocol.ProtocolUtil.writeSlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ContainerComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreContainerComponent extends AbstractItemComponent implements ContainerComponent {

	protected static final CharKey
	NBT_ITEM = CharKey.literal("item"),
	NBT_SLOT = CharKey.literal("slot");
	
	private List<ItemStack> slots;
	
	public CoreContainerComponent(NamespacedKey key) {
		super(key);
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (hasItems()) {
			writer.writeListTag(key.toString(), TagType.COMPOUND, 0);
			return;
		}
		final int size = slots.size();
		writer.writeListTag(key.toString(), TagType.COMPOUND, slots.size());
		for (int i = 0; i < size; i++) {
			writer.writeCompoundTag();
			writer.writeCompoundTag(NBT_ITEM);
			slots.get(i).toNBT(writer, systemData);
			writer.writeEndTag();
			writer.writeIntTag(NBT_SLOT, i);
			writer.writeEndTag();
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			ItemStack item = null;
			int slot = 0;
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (NBT_ITEM.equals(key)) {
					item = ItemStack.getFromNBT(reader);
				} else if (NBT_SLOT.equals(key)) {
					slot = reader.readIntTag();
				}
			}
			reader.readNextEntry();
			if (item == null)
				continue;
			getItems().add(slot, item);
		}
		reader.readNextEntry();
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
	public ComponentType getType() {
		return ComponentType.CONTAINER;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
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
