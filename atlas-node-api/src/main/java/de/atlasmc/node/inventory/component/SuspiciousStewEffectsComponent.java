package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.util.annotation.NotNull;

public interface SuspiciousStewEffectsComponent extends AbstractPotionEffectComponent {
	
	@NotNull
	public static final NBTCodec<SuspiciousStewEffectsComponent>
	NBT_CODEC = NBTCodec
					.builder(SuspiciousStewEffectsComponent.class)
					.include(AbstractPotionEffectComponent.NBT_CODEC)
					.codecList(ComponentType.SUSPICIOUS_STEW_EFFECTS.getNamespacedKey(), SuspiciousStewEffectsComponent::hasEffects, SuspiciousStewEffectsComponent::getEffects, PotionEffect.NBT_CODEC)
					.build();
	
	@Override
	SuspiciousStewEffectsComponent clone();
	
	@Override
	default NBTCodec<? extends SuspiciousStewEffectsComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
