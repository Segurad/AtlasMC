package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public interface BlockDataComponent extends AbstractBlockDataComponent {
	
	public static final NBTCodec<BlockDataComponent>
	NBT_HANDLER = NBTCodec
					.builder(BlockDataComponent.class)
					.include(AbstractBlockDataComponent.NBT_CODEC)
					.mapFieldNameToCodec(ComponentType.BLOCK_STATE.getNamespacedKey(), BlockDataComponent::hasProperties, BlockDataComponent::getProperties, NBTCodecs.STRING)
					.build();
	
	@Override
	default NBTCodec<? extends BlockDataComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
