package de.atlasmc.node.inventory.component;

import de.atlasmc.node.inventory.ItemPredicate;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface LockComponent extends ItemComponent {
	
	public static final NBTCodec<LockComponent>
	NBT_HANDLER = NBTCodec
					.builder(LockComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.typeCompoundField(ComponentType.LOCK.getNamespacedKey(), LockComponent::getPredicate, LockComponent::setPredicate, ItemPredicate.NBT_HANDLER)
					.build();
	
	ItemPredicate getPredicate();
	
	void setPredicate(ItemPredicate predicate);
	
	LockComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
