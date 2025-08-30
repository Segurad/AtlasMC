package de.atlasmc.inventory.component;

import de.atlasmc.potion.PotionEffect;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SuspiciousStewEffectsComponent extends AbstractPotionEffectComponent {
	
	public static final NBTSerializationHandler<SuspiciousStewEffectsComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SuspiciousStewEffectsComponent.class)
					.include(AbstractPotionEffectComponent.NBT_HANDLER)
					.typeList(ComponentType.SUSPICIOUS_STEW_EFFECTS.getNamespacedKey(), SuspiciousStewEffectsComponent::hasEffects, SuspiciousStewEffectsComponent::getEffects, PotionEffect.NBT_HANDLER)
					.build();
	
	SuspiciousStewEffectsComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends SuspiciousStewEffectsComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
