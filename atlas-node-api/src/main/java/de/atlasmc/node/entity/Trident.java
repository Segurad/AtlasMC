package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface Trident extends AbstractArrow {
	
	@NotNull
	public static final NBTCodec<Trident>
	NBT_CODEC = NBTCodec
					.builder(Trident.class)
					.include(AbstractArrow.NBT_CODEC)
					.boolField("DealtDamage", Trident::hasDealtDamage, Trident::setDealtDamage, false)
					.codec("item", Trident::getItem, Trident::setItem, ItemStack.NBT_CODEC)
					.build();
	
	int getLoyalityLevel();
	
	void setLoyalityLevel(int level);
	
	boolean hasEnchantmentGlint();

	void setEnchantmentGlint(boolean glint);

	boolean hasDealtDamage();
	
	void setDealtDamage(boolean dealtDamage);
	
	@Override
	void setItem(ItemStack item);
	
	@Override
	ItemStack getItem();
	
	boolean hasItem();
	
	@Override
	default NBTCodec<? extends AbstractArrow> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
