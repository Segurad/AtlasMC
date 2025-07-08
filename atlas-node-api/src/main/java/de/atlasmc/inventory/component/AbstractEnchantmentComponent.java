package de.atlasmc.inventory.component;

import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface AbstractEnchantmentComponent extends AbstractTooltipComponent {
	
	static final NBTSerializationHandler<AbstractEnchantmentComponent>
	NBT_HANDLER = NBTSerializationHandler
			.builder(AbstractEnchantmentComponent.class)
			.include(AbstractTooltipComponent.NBT_HANDLER)
			.compoundMapNamespaced2Int("levels", AbstractEnchantmentComponent::hasEnchants, AbstractEnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	void addEnchant(Enchantment ench, int level);
	
	int getEnchantLevel(Enchantment ench);
	
	Object2IntMap<Enchantment> getStoredEnchants();
	
	boolean hasConflictingEnchant(Enchantment ench);
	
	boolean hasEnchants();
	
	boolean hasEnchant(Enchantment ench);
	
	void removeEnchant(Enchantment ench);
	
	AbstractTooltipComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends AbstractEnchantmentComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
