package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.util.annotation.NotNull;

public interface CanBreakComponent extends AbstractBlockPredicateComponent {
	
	@NotNull
	public static final NBTCodec<CanBreakComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(CanBreakComponent.class, BlockPredicate.NBT_CODEC)
				.include(AbstractBlockPredicateComponent.NBT_CODEC)
				.hasData(CanBreakComponent::hasPredicates)
				.getter(CanBreakComponent::getPredicates)
				.build();
	
	@Override
	CanBreakComponent clone();
	
	@Override
	default NBTCodec<? extends CanBreakComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
