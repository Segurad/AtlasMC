package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.inventory.component.AbstractBlockDataComponent;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreAbstractBlockDataComponent extends AbstractItemComponent implements AbstractBlockDataComponent {

	private Map<BlockDataProperty<?>, Object> properties;
	
	public CoreAbstractBlockDataComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public CoreAbstractBlockDataComponent clone() {
		CoreAbstractBlockDataComponent clone = (CoreAbstractBlockDataComponent) super.clone();
		if (clone == null)
			return null;
		if (properties != null) {
			clone.properties = new HashMap<>(properties);
		}
		return clone;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (properties == null)
			return;
		writer.writeCompoundTag(getNamespacedKeyRaw());
		BlockDataProperty.writeProperties(properties, writer, systemData);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		if (reader.getType() == TagType.TAG_END) {
			reader.readNextEntry();
			return;
		}
		Map<BlockDataProperty<?>, Object> properties = BlockDataProperty.readProperties(reader);
		if (properties == null || properties.isEmpty())
			return;
		setProperties(properties);
	}

	@Override
	public Map<BlockDataProperty<?>, Object> getProperties() {
		if (properties == null)
			properties = new HashMap<>();
		return properties;
	}

	@Override
	public void setProperties(Map<BlockDataProperty<?>, Object> properties) {
		if (this.properties == null) {
			this.properties = new HashMap<>(properties);
		} else {
			this.properties.clear();
			this.properties.putAll(properties);
		}
	}

	@Override
	public <T> void setProperty(BlockDataProperty<T> property, T value) {
		properties.put(property, value);
	}

	@Override
	public boolean hasProperties() {
		return properties != null && !properties.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(BlockDataProperty<T> property) {
		Object value = properties.get(property);
		if (value == null)
			return null;
		return (T) value;
	}

	@Override
	public void applyTo(BlockData data) {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (!hasProperties())
			return;
		for (Entry<BlockDataProperty<?>, Object> entry : properties.entrySet()) {
			@SuppressWarnings("unchecked")
			BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
			property.set(data, entry.getValue());
		}
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int count = readVarInt(buf);
		if (count == 0)
			return;
		Map<BlockDataProperty<?>, Object> properties = getProperties();
		properties.clear();
		for (int i = 0; i < count; i++) {
			String key = readString(buf);
			String rawValue = readString(buf);
			Collection<BlockDataProperty<?>> propertyTypes = BlockDataProperty.getProperties(key);
			for (BlockDataProperty<?> property : propertyTypes) {
				Object value = property.fromString(rawValue);
				if (value == null)
					continue;
				properties.put(property, value);
				break;
			}
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (properties == null || properties.isEmpty()) {
			writeVarInt(0, buf);
			return;
		}
		Map<BlockDataProperty<?>, Object> properties = this.properties;
		final int size = properties.size();
		writeVarInt(size, buf);
		for (Entry<BlockDataProperty<?>, Object> entry : properties.entrySet()) {
			@SuppressWarnings("unchecked")
			BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
			writeString(property.getKey(), buf);
			writeString(property.toString(entry.getValue()), buf);
		}
	}

}
