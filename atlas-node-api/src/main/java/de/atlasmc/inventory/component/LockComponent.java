package de.atlasmc.inventory.component;

import de.atlasmc.inventory.ItemPredicate;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface LockComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<LockComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(LockComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(ComponentType.LOCK.getNamespacedKey(), LockComponent::getPredicate, LockComponent::setPredicate, ItemPredicate.NBT_HANDLER)
					.build();
	
	ItemPredicate getPredicate();
	
	void setPredicate(ItemPredicate predicate);
	
	LockComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
