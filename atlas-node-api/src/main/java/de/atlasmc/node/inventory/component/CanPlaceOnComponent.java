package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockPredicate;

public interface CanPlaceOnComponent extends AbstractBlockPredicateComponent {
	
	public static final NBTCodec<CanPlaceOnComponent>
	NBT_HANDLER = NBTCodec
					.builder(CanPlaceOnComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_CODEC)
					.codecList(ComponentType.CAN_PLACE_ON.getNamespacedKey(), CanPlaceOnComponent::hasPredicates, CanPlaceOnComponent::getPredicates, BlockPredicate.NBT_HANDLER)
					.build();

	CanPlaceOnComponent clone();
	
	@Override
	default NBTCodec<? extends CanPlaceOnComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
