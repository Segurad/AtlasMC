package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ThrowableProjectile extends Projectile {
	
	public static final NBTSerializationHandler<ThrowableProjectile>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ThrowableProjectile.class)
					.include(Projectile.NBT_HANDLER)
					.typeCompoundField("Item", ThrowableProjectile::getItem, ThrowableProjectile::setItem, ItemStack.NBT_HANDLER)
					.build();

	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	@Override
	default NBTSerializationHandler<? extends ThrowableProjectile> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
