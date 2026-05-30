package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.util.annotation.NotNull;

public interface CanPlaceOnComponent extends AbstractBlockPredicateComponent {
	
	@NotNull
	public static final NBTCodec<CanPlaceOnComponent>
	NBT_HANDLER = NBTCodec
					.builder(CanPlaceOnComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_CODEC)
					.codecList(ComponentType.CAN_PLACE_ON.getNamespacedKey(), CanPlaceOnComponent::hasPredicates, CanPlaceOnComponent::getPredicates, BlockPredicate.NBT_CODEC)
					.build();

	@Override
	CanPlaceOnComponent clone();
	
	@Override
	default NBTCodec<? extends CanPlaceOnComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
