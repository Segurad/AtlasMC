package de.atlascore.block.data;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.util.annotation.NotNull;

public class CoreBlockData implements BlockData {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES = List.of();
	
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
