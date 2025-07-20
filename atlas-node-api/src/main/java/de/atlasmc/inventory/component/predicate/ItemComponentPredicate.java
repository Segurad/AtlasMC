package de.atlasmc.inventory.component.predicate;

import java.util.function.Predicate;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemComponentPredicate extends Namespaced, NBTSerializable, Predicate<ItemComponent> {
	
	public static final NBTSerializationHandler<ItemComponentPredicate>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemComponentPredicate.class)
					.build();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponentPredicate> getNBTHandler() {
		return NBT_HANDLER;
	}

}
