package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.CustomDataComponent;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;
import io.netty.buffer.ByteBuf;

public class CoreCustomDataComponent extends AbstractItemComponent implements CustomDataComponent {

	private Map<String, NBT> values;
	
	public CoreCustomDataComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreCustomDataComponent clone() {
		CoreCustomDataComponent clone = (CoreCustomDataComponent) super.clone();
		if (values != null) {
			clone.values = new HashMap<>(values);
		}
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (hasValues()) {
			for (NBT value : values.values())
				writer.writeNBT(value);
		}
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		switch (reader.getType()) {
		case STRING: {
			reader.readStringTag();
			// TODO read from string
		}
		case COMPOUND: {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				NBT nbt = reader.readNBT();
				add(nbt);
			}
			reader.readNextEntry();
		}
		default:
			throw new IllegalArgumentException("Unexpected type: " + reader.getType());
		}
	}

	@Override
	public void add(NBT nbt) {
		if (nbt == null)
			throw new IllegalArgumentException("NBT can not b null!");
		String key = nbt.getName();
		if (key == null)
			throw new IllegalArgumentException("NBT does not have name!");
		getValues().put(key, nbt);
	}

	@Override
	public void removeNBT(NBT nbt) {
		if (values != null)
			values.remove(nbt.getName(), nbt);
	}

	@Override
	public void removeNBT(String key) {
		if (values != null)
			values.remove(key);
	}

	@Override
	public Map<String, NBT> getValues() {
		if (values == null)
			values = new HashMap<>();
		return values;
	}

	@Override
	public boolean hasValues() {
		return values != null && !values.isEmpty();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CUSTOM_DATA;
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTNIOWriter writer = new NBTNIOWriter(buf, true);
		toNBT(writer, false);
		writer.close();
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(buf, true);
		if (values != null)
			values.clear();
		fromNBT(reader);
		reader.close();
	}

}
