package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockPredicate;

public interface CanBreakComponent extends AbstractBlockPredicateComponent {
	
	public static final NBTCodec<CanBreakComponent>
	NBT_HANDLER = NBTCodec
					.builder(CanBreakComponent.class)
					.include(AbstractBlockPredicateComponent.NBT_CODEC)
					.codecList(ComponentType.CAN_BREAK.getNamespacedKey(), CanBreakComponent::hasPredicates, CanBreakComponent::getPredicates, BlockPredicate.NBT_HANDLER)
					.build();
	
	CanBreakComponent clone();
	
	@Override
	default NBTCodec<? extends CanBreakComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
