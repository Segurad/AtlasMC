package de.atlasmc.node.inventory.component;

import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BlockDataComponent extends AbstractBlockDataComponent {
	
	public static final NBTSerializationHandler<BlockDataComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlockDataComponent.class)
					.include(AbstractBlockDataComponent.NBT_HANDLER)
					.addField(BlockDataProperty.getBlockDataPropertiesMapField(ComponentType.BLOCK_STATE.getNamespacedKey(), BlockDataComponent::hasProperties, BlockDataComponent::getProperties))
					.build();
	
	BlockDataComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends BlockDataComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
