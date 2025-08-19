package de.atlasmc.inventory.component;

import de.atlasmc.block.BlockPredicate;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface CanPlaceOnComponent extends AbstractBlockPredicateComponent {
	
	public static final NBTSerializationHandler<CanPlaceOnComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CanPlaceOnComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_HANDLER)
					.typeList(ComponentType.CAN_PLACE_ON, CanPlaceOnComponent::hasPredicates, CanPlaceOnComponent::getPredicates, BlockPredicate.NBT_HANDLER)
					.build();

	CanPlaceOnComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends CanPlaceOnComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
