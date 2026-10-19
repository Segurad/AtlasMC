package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecCollectionFieldCodec;
import de.atlasmc.nbt.codec.MapFieldNameToCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface BlockDataComponent extends AbstractBlockDataComponent {
	
	@NotNull
	public static final NBTCodec<BlockDataComponent>
	NBT_CODEC = CodecCollectionFieldCodec
				.builder(BlockDataComponent.class, new MapFieldNameToCodec<>(NBTCodecs.STRING))
				.include(AbstractBlockDataComponent.NBT_CODEC)
				.hasData(BlockDataComponent::hasProperties)
				.getter(BlockDataComponent::getProperties)
				.build();
	
	@Override
	default NBTCodec<? extends BlockDataComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
