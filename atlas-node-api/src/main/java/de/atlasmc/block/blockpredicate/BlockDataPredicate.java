package de.atlasmc.block.blockpredicate;

import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.tile.TileEntity;

public class BlockDataPredicate implements BlockPredicate {
	
	private Map<BlockDataProperty<?>, Object> properties;
	
	public BlockDataPredicate(Map<BlockDataProperty<?>, Object> properties) {
		setProperties(properties);
	}
	
	public Map<BlockDataProperty<?>, Object> getProperties() {
		return properties;
	}
	
	public void setProperties(Map<BlockDataProperty<?>, Object> properties) {
		if (properties == null)
			throw new IllegalArgumentException("Properties can not be null!");
		this.properties = properties;
	}

	@Override
	public boolean matches(TileEntity tile) {
		return false;
	}

	@Override
	public boolean matches(BlockData data) {
		if (data == null)
			return false;
		for (Entry<BlockDataProperty<?>, Object> entry : properties.entrySet()) {
			@SuppressWarnings("unchecked")
			BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
			Object value = entry.getValue();
			property.match(data, value);
		}
		return false;
	}

	@Override
	public boolean matches(BlockType type) {
		return false;
	}

}
