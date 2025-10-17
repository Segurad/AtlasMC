package de.atlasmc.node.inventory.component;

import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface CanBreakComponent extends AbstractBlockPredicateComponent {
	
	public static final NBTCodec<CanBreakComponent>
	NBT_HANDLER = NBTCodec
					.builder(CanBreakComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_HANDLER)
					.typeList(ComponentType.CAN_BREAK.getNamespacedKey(), CanBreakComponent::hasPredicates, CanBreakComponent::getPredicates, BlockPredicate.NBT_HANDLER)
					.build();
	
	CanBreakComponent clone();
	
	@Override
	default NBTCodec<? extends CanBreakComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
