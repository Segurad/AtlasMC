package de.atlasmc.node.block;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class BlockPredicate implements NBTSerializable {

	public static final NBTSerializationHandler<BlockPredicate>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlockPredicate.class)
					.defaultConstructor(BlockPredicate::new)
					.dataSetField("blocks", BlockPredicate::getTypes, BlockPredicate::setTypes, BlockType.getRegistry())
					.typeCompoundField("nbt", BlockPredicate::getTile, BlockPredicate::setTile, TileEntity.NBT_HANDLER)
					.addField(BlockDataProperty.getBlockDataPropertiesMapField("state", BlockPredicate::hasStates, BlockPredicate::getStates))
					.build();

	private DataSet<BlockType> types;
	private TileEntity tile;
	private Map<BlockDataProperty<?>, Object> states;
	
	public DataSet<BlockType> getTypes() {
		return types;
	}
	
	public void setTypes(DataSet<BlockType> types) {
		this.types = types;
	}
	
	public TileEntity getTile() {
		return tile;
	}
	
	public void setTile(TileEntity tile) {
		this.tile = tile;
	}
	
	public boolean hasStates() {
		return states != null && !states.isEmpty();
	}
	
	public Map<BlockDataProperty<?>, Object> getStates() {
		if (states == null)
			states = new HashMap<>();
		return states;
	}
	
	public void setStates(Map<BlockDataProperty<?>, Object> states) {
		Map<BlockDataProperty<?>, Object> map = getStates();
		map.clear();
		map.putAll(states);
	}
	
	@Override
	public NBTSerializationHandler<? extends NBTSerializable> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
