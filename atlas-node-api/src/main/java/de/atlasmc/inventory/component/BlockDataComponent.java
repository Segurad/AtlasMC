package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BlockDataComponent extends AbstractBlockDataComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:block_state");
	
	public static final NBTSerializationHandler<BlockDataComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlockDataComponent.class)
					.include(AbstractBlockDataComponent.NBT_HANDLER)
					.addField(BlockDataProperty.getBlockDataPropertiesMapField(COMPONENT_KEY, BlockDataComponent::hasProperties, BlockDataComponent::getProperties))
					.build();
	
	BlockDataComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends BlockDataComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
