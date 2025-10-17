package de.atlasmc.node.inventory.component;

import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface CanPlaceOnComponent extends AbstractBlockPredicateComponent {
	
	public static final NBTCodec<CanPlaceOnComponent>
	NBT_HANDLER = NBTCodec
					.builder(CanPlaceOnComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_HANDLER)
					.typeList(ComponentType.CAN_PLACE_ON.getNamespacedKey(), CanPlaceOnComponent::hasPredicates, CanPlaceOnComponent::getPredicates, BlockPredicate.NBT_HANDLER)
					.build();

	CanPlaceOnComponent clone();
	
	@Override
	default NBTCodec<? extends CanPlaceOnComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
