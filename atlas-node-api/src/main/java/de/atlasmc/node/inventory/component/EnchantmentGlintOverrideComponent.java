package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface EnchantmentGlintOverrideComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<EnchantmentGlintOverrideComponent>
	NBT_CODEC = NBTCodec
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.boolField(ComponentType.ENCHANTMENT_GLINT_OVERRIDE.getNamespacedKey(), EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	@NotNull
	public static final StreamCodec<EnchantmentGlintOverrideComponent>
	STREAM_CODEC = StreamCodec
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.booleanValue(EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	boolean hasGlint();
	
	void setGlint(boolean glint);
	
	@Override
	EnchantmentGlintOverrideComponent clone();
	
	@Override
	default NBTCodec<? extends EnchantmentGlintOverrideComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends EnchantmentGlintOverrideComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
