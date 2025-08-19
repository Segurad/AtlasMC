package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DeathProtectionComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<DeathProtectionComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(DeathProtectionComponent.class)
					.beginComponent(ComponentType.DEATH_PROTECTION)
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
