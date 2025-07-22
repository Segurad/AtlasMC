package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Trident extends AbstractArrow {
	
	public static final NBTSerializationHandler<Trident>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends AbstractArrow> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
