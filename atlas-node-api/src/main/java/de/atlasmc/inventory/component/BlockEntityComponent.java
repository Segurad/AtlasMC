package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BlockEntityComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:block_entity_data");
	
	public static final NBTSerializationHandler<BlockEntityComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlockEntityComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.compoundType(COMPONENT_KEY, BlockEntityComponent::getTileEntity, BlockEntityComponent::setTileEntity, TileEntity.NBT_HANDLER)
					.build();
	
	@Override
	BlockEntityComponent clone();
	
	TileEntity getTileEntity();
	
	void setTileEntity(TileEntity tile);
	
	@Override
	default NBTSerializationHandler<? extends BlockEntityComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
