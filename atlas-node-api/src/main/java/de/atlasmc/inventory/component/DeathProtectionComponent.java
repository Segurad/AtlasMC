package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DeathProtectionComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:death_protection");
	
	public static final NBTSerializationHandler<DeathProtectionComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(DeathProtectionComponent.class)
					.beginComponent(COMPONENT_KEY)
					.typeList("death_effects", DeathProtectionComponent::hasEffects, DeathProtectionComponent::getEffects, ComponentEffect.NBT_HANDLER)
					.endComponent()
					.build();
	
	List<ComponentEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(ComponentEffect effect);
	
	void removeEffect(ComponentEffect effect);

	DeathProtectionComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends DeathProtectionComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
