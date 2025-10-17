package de.atlasmc.node.inventory.component;

import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BlockEntityComponent extends ItemComponent {
	
	public static final NBTCodec<BlockEntityComponent>
	NBT_HANDLER = NBTCodec
					.builder(BlockEntityComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(ComponentType.BLOCK_ENTITY_DATA.getNamespacedKey(), BlockEntityComponent::getTileEntity, BlockEntityComponent::setTileEntity, TileEntity.NBT_HANDLER)
					.build();
	
	@Override
	BlockEntityComponent clone();
	
	TileEntity getTileEntity();
	
	void setTileEntity(TileEntity tile);
	
	@Override
	default NBTCodec<? extends BlockEntityComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
