package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.TileEntity;

public interface BlockEntityComponent extends ItemComponent {
	
	public static final NBTCodec<BlockEntityComponent>
	NBT_CODEC = NBTCodec
					.builder(BlockEntityComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.BLOCK_ENTITY_DATA.getNamespacedKey(), BlockEntityComponent::getTileEntity, BlockEntityComponent::setTileEntity, TileEntity.NBT_HANDLER)
					.build();
	
	public static final StreamCodec<BlockEntityComponent>
	STREAM_CODEC = StreamCodec
					.builder(BlockEntityComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(BlockEntityComponent::getTileEntity, BlockEntityComponent::setTileEntity, TileEntity.NBT_HANDLER)
					.build();
	
	@Override
	BlockEntityComponent clone();
	
	TileEntity getTileEntity();
	
	void setTileEntity(TileEntity tile);
	
	@Override
	default NBTCodec<? extends BlockEntityComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends BlockEntityComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
