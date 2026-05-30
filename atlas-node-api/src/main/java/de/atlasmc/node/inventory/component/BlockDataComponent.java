package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface BlockDataComponent extends AbstractBlockDataComponent {
	
	@NotNull
	public static final NBTCodec<BlockDataComponent>
	NBT_CODEC = NBTCodec
					.builder(BlockDataComponent.class)
					.include(AbstractBlockDataComponent.NBT_CODEC)
					.mapFieldNameToCodec(ComponentType.BLOCK_STATE.getNamespacedKey(), BlockDataComponent::hasProperties, BlockDataComponent::getProperties, NBTCodecs.STRING)
					.build();
	
	@Override
	default NBTCodec<? extends BlockDataComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
