package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface ThrowableProjectile extends Projectile {
	
	@NotNull
	public static final NBTCodec<ThrowableProjectile>
	NBT_CODEC = NBTCodec
					.builder(ThrowableProjectile.class)
					.include(Projectile.NBT_CODEC)
					.codec("Item", ThrowableProjectile::getItem, ThrowableProjectile::setItem, ItemStack.NBT_CODEC)
					.build();

	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	@Override
	default NBTCodec<? extends ThrowableProjectile> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
