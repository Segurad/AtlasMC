package de.atlasmc.node.block;

import java.util.ArrayList;
import java.util.List;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.block.data.property.PropertyValuePredicate;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.util.dataset.DataSet;

public class BlockPredicate implements NBTSerializable, StreamSerializable {

	public static final NBTCodec<BlockPredicate>
	NBT_HANDLER = NBTCodec
					.builder(BlockPredicate.class)
					.defaultConstructor(BlockPredicate::new)
					.codec("blocks", BlockPredicate::getTypes, BlockPredicate::setTypes, DataSet.nbtCodec(BlockType.REGISTRY_KEY))
					.codec("nbt", BlockPredicate::getTile, BlockPredicate::setTile, TileEntity.NBT_HANDLER)
					.codecCollection("state", BlockPredicate::hasStates, BlockPredicate::getStates, PropertyValuePredicate.LIST_NBT_CODEC)
					.build();
	
	public static final StreamCodec<BlockPredicate>
	STREAM_CODEC = StreamCodec
					.builder(BlockPredicate.class)
					.defaultConstructor(BlockPredicate::new)
					.optional(BlockPredicate::hasTypes)
					.codec(BlockPredicate::getTypes, BlockPredicate::setTypes, DataSet.streamCodec(BlockType.REGISTRY_KEY))
					.optional(BlockPredicate::hasStates)
					.listCodec(BlockPredicate::hasStates, BlockPredicate::getStates, PropertyValuePredicate.STREAM_CODEC)
					.optional(BlockPredicate::hasTile)
					.codec(BlockPredicate::getTile, BlockPredicate::setTile, TileEntity.NBT_HANDLER)
					.staticBooleanValue(false) // TODO match data component
					.staticBooleanValue(false) // TODO data component predicates
					.build();

	private DataSet<BlockType> types;
	private TileEntity tile;
	private List<PropertyValuePredicate> states;
	
	public boolean hasTypes() {
		return types != null && !types.isEmpty();
	}
	
	public DataSet<BlockType> getTypes() {
		return types;
	}
	
	public void setTypes(DataSet<BlockType> types) {
		this.types = types;
	}
	
	public boolean hasTile() {
		return tile != null;
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
	
	public List<PropertyValuePredicate> getStates() {
		if (states == null)
			states = new ArrayList<>();
		return states;
	}
	
	@Override
	public NBTCodec<? extends BlockPredicate> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	public StreamCodec<? extends BlockPredicate> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
