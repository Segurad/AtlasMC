package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BlockDataComponent;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockDataComponent extends AbstractItemComponent implements BlockDataComponent {

	private Map<BlockDataProperty<?>, Object> properties;
	
	public CoreBlockDataComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public CoreBlockDataComponent clone() {
		CoreBlockDataComponent clone = (CoreBlockDataComponent) super.clone();
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

}
