package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnchantmentGlintOverrideComponent extends ItemComponent {

	public static final NBTSerializationHandler<EnchantmentGlintOverrideComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.boolField(ComponentType.ENCHANTMENT_GLINT_OVERRIDE.getNamespacedKey(), EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	boolean hasGlint();
	
	void setGlint(boolean glint);
	
	EnchantmentGlintOverrideComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends EnchantmentGlintOverrideComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
