package de.atlascore.block.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockData implements BlockData {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES = List.of();
	
	private final Material material;
	
	public CoreBlockData(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) 
			throw new IllegalArgumentException("Material is not a Block: " + material.getNamespacedKeyRaw());
		this.material = material;
	}
	
	public BlockData clone() {
		try {
			return (BlockData) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public int getStateID() {
		return material.getBlockStateID();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		List<BlockDataProperty<?>> properties = getProperties();
		if (properties.isEmpty())
			return;
		final int size = properties.size();
		for (int i = 0; i < size; i++) {
			BlockDataProperty<?> property = properties.get(i);
			property.dataToNBT(this, writer, systemData);
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		while (reader.getType() != TagType.TAG_END) {
			CharSequence key = reader.getFieldName();
			TagType type = reader.getType();
			BlockDataProperty<?> property = BlockDataProperty.getProperty(key, type);
			if (property == null) {
				reader.skipTag();
				continue;
			}
			property.dataFromNBT(this, reader);
		}
		reader.readNextEntry();
	}
	
	protected static List<BlockDataProperty<?>> merge(List<BlockDataProperty<?>> parent, BlockDataProperty<?>... values) {
		ArrayList<BlockDataProperty<?>> newList = new ArrayList<>(parent.size() + values.length);
		newList.addAll(parent);
		for (BlockDataProperty<?> property : values) {
			if (newList.contains(property))
				continue;
			newList.add(property);
		}
		return List.copyOf(newList);
	}

}
