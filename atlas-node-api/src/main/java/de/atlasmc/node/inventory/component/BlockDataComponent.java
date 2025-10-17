package de.atlasmc.node.inventory.component;

import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BlockDataComponent extends AbstractBlockDataComponent {
	
	public static final NBTCodec<BlockDataComponent>
	NBT_HANDLER = NBTCodec
					.builder(BlockDataComponent.class)
					.include(AbstractBlockDataComponent.NBT_HANDLER)
					.addField(BlockDataProperty.getBlockDataPropertiesMapField(ComponentType.BLOCK_STATE.getNamespacedKey(), BlockDataComponent::hasProperties, BlockDataComponent::getProperties))
					.build();
	
	BlockDataComponent clone();
	
	@Override
	default NBTCodec<? extends BlockDataComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
