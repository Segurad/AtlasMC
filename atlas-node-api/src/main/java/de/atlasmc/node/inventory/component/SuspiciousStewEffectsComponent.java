package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.potion.PotionEffect;

public interface SuspiciousStewEffectsComponent extends AbstractPotionEffectComponent {
	
	public static final NBTCodec<SuspiciousStewEffectsComponent>
	NBT_HANDLER = NBTCodec
					.builder(SuspiciousStewEffectsComponent.class)
					.include(AbstractPotionEffectComponent.NBT_CODEC)
					.codecList(ComponentType.SUSPICIOUS_STEW_EFFECTS.getNamespacedKey(), SuspiciousStewEffectsComponent::hasEffects, SuspiciousStewEffectsComponent::getEffects, PotionEffect.NBT_CODEC)
					.build();
	
	SuspiciousStewEffectsComponent clone();
	
	@Override
	default NBTCodec<? extends SuspiciousStewEffectsComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
