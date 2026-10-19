package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.util.annotation.NotNull;

public interface CanPlaceOnComponent extends AbstractBlockPredicateComponent {
	
	@NotNull
	public static final NBTCodec<CanPlaceOnComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(CanPlaceOnComponent.class, BlockPredicate.NBT_CODEC)
				.include(AbstractBlockPredicateComponent.NBT_CODEC)
				.hasData(CanPlaceOnComponent::hasPredicates)
				.getter(CanPlaceOnComponent::getPredicates)
				.build();

	@Override
	CanPlaceOnComponent clone();
	
	@Override
	default NBTCodec<? extends CanPlaceOnComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
