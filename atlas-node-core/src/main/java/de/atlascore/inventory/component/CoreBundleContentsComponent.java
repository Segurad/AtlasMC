package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BundleContentsComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreBundleContentsComponent extends AbstractItemComponent implements BundleContentsComponent {

	private static final int DEFAULT_ITEMS_SIZE = 5;
	
	private List<ItemStack> items;
	
	public CoreBundleContentsComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public CoreBundleContentsComponent clone() {
		CoreBundleContentsComponent clone = (CoreBundleContentsComponent) super.clone();
		if (clone == null)
			return null;
		return clone;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (items == null || items.isEmpty())
			return;
		List<ItemStack> items = this.items;
		final int size = items.size();
		writer.writeListTag(getNamespacedKeyRaw(), TagType.COMPOUND, size);
		for (int i = 0; i < size; i++) {
			ItemStack item = items.get(i);
			item.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			ItemStack item = ItemStack.getFromNBT(reader);
			addItem(item);
		}
		reader.readNextEntry();
	}

	@Override
	public List<ItemStack> getItems() {
		if (items == null)
			items = new ArrayList<>(DEFAULT_ITEMS_SIZE);
		return items;
	}

	@Override
	public boolean hasItems() {
		return items != null && !items.isEmpty();
	}

	@Override
	public void addItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getItems().add(item);
	}

	@Override
	public void removeItem(ItemStack item) {
		if (items == null)
			return;
		items.remove(item);
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.BUNDLE_CONTENTS;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int count = readVarInt(buf);
		if (count == 0)
			return;
		List<ItemStack> items = getItems();
		items.clear();
		for (int i = 0; i < count; i++) {
			items.add(readSlot(buf));
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (items == null || items.isEmpty()) {
			writeVarInt(0, buf);
			return;
		}
		List<ItemStack> items = getItems();
		final int size = items.size();
		writeVarInt(size, buf);
		for (int i = 0; i < size; i++) {
			ItemStack item = items.get(i);
			writeSlot(item, buf);
		}
	}

}
