package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EnchantmentGlintOverrideComponent extends ItemComponent {

	public static final NBTCodec<EnchantmentGlintOverrideComponent>
	NBT_HANDLER = NBTCodec
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.boolField(ComponentType.ENCHANTMENT_GLINT_OVERRIDE.getNamespacedKey(), EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	boolean hasGlint();
	
	void setGlint(boolean glint);
	
	EnchantmentGlintOverrideComponent clone();
	
	@Override
	default NBTCodec<? extends EnchantmentGlintOverrideComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
