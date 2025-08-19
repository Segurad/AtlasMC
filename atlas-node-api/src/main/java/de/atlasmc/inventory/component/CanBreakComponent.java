package de.atlasmc.inventory.component;

import de.atlasmc.block.BlockPredicate;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface CanBreakComponent extends AbstractBlockPredicateComponent {
	
	public static final NBTSerializationHandler<CanBreakComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CanBreakComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_HANDLER)
					.typeList(ComponentType.CAN_BREAK, CanBreakComponent::hasPredicates, CanBreakComponent::getPredicates, BlockPredicate.NBT_HANDLER)
					.build();
	
	CanBreakComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends CanBreakComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
