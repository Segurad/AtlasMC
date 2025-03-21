package de.atlascore.block.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockData implements BlockData {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES = List.of();
	protected static final NBTFieldSet<CoreBlockData> NBT_FIELDS;
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_NAME, NBTField.skip());
		NBT_FIELDS.setField(NBT_PROPERTIES, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				TagType type = reader.getType();
				BlockDataProperty<?> property = BlockDataProperty.getProperty(key, type);
				if (property == null) {
					reader.skipTag();
					continue;
				}
				property.dataFromNBT(holder, reader);
			}
			reader.readNextEntry();
		});
	}
	
	protected final BlockType type;
	
	public CoreBlockData(BlockType material) {
		if (material == null) 
			throw new IllegalArgumentException("BlockType can not be null!");
		this.type = material;
	}
	
	public BlockData clone() {
		try {
			return (BlockData) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	public BlockType getType() {
		return type;
	}

	@Override
	public int getStateID() {
		return type.getBlockStateID();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_NAME, type.getNamespacedKeyRaw());
		writer.writeCompoundTag(NBT_PROPERTIES);
		BlockDataProperty.writeProperties(this, writer, systemData);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}
	
	/**
	 * Merges a property list with the given properties and returns a immutable copy
	 * @param parent the parent property list
	 * @param values the properties to add
	 * @return list
	 */
	@NotNull
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
