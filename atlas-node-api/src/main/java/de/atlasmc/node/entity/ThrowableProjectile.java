package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ThrowableProjectile extends Projectile {
	
	public static final NBTCodec<ThrowableProjectile>
	NBT_HANDLER = NBTCodec
					.builder(ThrowableProjectile.class)
					.include(Projectile.NBT_HANDLER)
					.typeCompoundField("Item", ThrowableProjectile::getItem, ThrowableProjectile::setItem, ItemStack.NBT_HANDLER)
					.build();

	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	@Override
	default NBTCodec<? extends ThrowableProjectile> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
