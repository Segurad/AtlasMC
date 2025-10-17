package de.atlasmc.node.inventory.component.predicate;

import java.util.function.Predicate;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ItemComponent;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ItemComponentPredicate extends Namespaced, NBTSerializable, Predicate<ItemComponent> {
	
	public static final NBTCodec<ItemComponentPredicate>
	NBT_HANDLER = NBTCodec
					.builder(ItemComponentPredicate.class)
					.build();
	
	ComponentType getType();
	
	@Override
	default NBTCodec<? extends ItemComponentPredicate> getNBTCodec() {
		return NBT_HANDLER;
	}

}
