package de.atlasmc.node.block;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public class BlockPredicate implements NBTSerializable {

	public static final NBTCodec<BlockPredicate>
	NBT_HANDLER = NBTCodec
					.builder(BlockPredicate.class)
					.defaultConstructor(BlockPredicate::new)
					.dataSetField("blocks", BlockPredicate::getTypes, BlockPredicate::setTypes, BlockType.REGISTRY_KEY)
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
	public NBTCodec<? extends NBTSerializable> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
