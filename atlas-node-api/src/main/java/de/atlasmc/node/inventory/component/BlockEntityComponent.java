package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.util.annotation.NotNull;

public interface BlockEntityComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<BlockEntityComponent>
	NBT_CODEC = NBTCodec
					.builder(BlockEntityComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.BLOCK_ENTITY_DATA.getNamespacedKey(), BlockEntityComponent::getTileEntity, BlockEntityComponent::setTileEntity, TileEntity.NBT_CODEC)
					.build();
	
	@NotNull
	public static final StreamCodec<BlockEntityComponent>
	STREAM_CODEC = StreamCodec
					.builder(BlockEntityComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(BlockEntityComponent::getTileEntity, BlockEntityComponent::setTileEntity, TileEntity.NBT_CODEC)
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
