package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnchantmentGlintOverrideComponent extends ItemComponent {

	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:enchantment_glint_override");
	
	public static final NBTSerializationHandler<EnchantmentGlintOverrideComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.boolField(COMPONENT_KEY, EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	boolean hasGlint();
	
	void setGlint(boolean glint);
	
	EnchantmentGlintOverrideComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends EnchantmentGlintOverrideComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
