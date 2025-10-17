package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Trident extends AbstractArrow {
	
	public static final NBTCodec<Trident>
	NBT_HANDLER = NBTCodec
					.builder(Trident.class)
					.include(AbstractArrow.NBT_HANDLER)
					.boolField("DealtDamage", Trident::hasDealtDamage, Trident::setDealtDamage, false)
					.typeCompoundField("item", Trident::getItem, Trident::setItem, ItemStack.NBT_HANDLER)
					.build();
	
	int getLoyalityLevel();
	
	void setLoyalityLevel(int level);
	
	boolean hasEnchantmentGlint();

	void setEnchantmentGlint(boolean glint);

	boolean hasDealtDamage();
	
	void setDealtDamage(boolean dealtDamage);
	
	void setItem(ItemStack item);
	
	ItemStack getItem();
	
	boolean hasItem();
	
	@Override
	default NBTCodec<? extends AbstractArrow> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
