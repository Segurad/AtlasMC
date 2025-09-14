package de.atlasmc.node.inventory.component.predicate;

import java.util.function.Predicate;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ItemComponent;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemComponentPredicate extends Namespaced, NBTSerializable, Predicate<ItemComponent> {
	
	public static final NBTSerializationHandler<ItemComponentPredicate>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemComponentPredicate.class)
					.build();
	
	ComponentType getType();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponentPredicate> getNBTHandler() {
		return NBT_HANDLER;
	}

}
