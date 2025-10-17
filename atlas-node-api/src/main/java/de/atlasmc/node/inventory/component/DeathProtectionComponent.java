package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.node.inventory.component.effect.ComponentEffect;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface DeathProtectionComponent extends ItemComponent {
	
	public static final NBTCodec<DeathProtectionComponent>
	NBT_HANDLER = NBTCodec
					.builder(DeathProtectionComponent.class)
					.beginComponent(ComponentType.DEATH_PROTECTION.getNamespacedKey())
					.typeList("death_effects", DeathProtectionComponent::hasEffects, DeathProtectionComponent::getEffects, ComponentEffect.NBT_HANDLER)
					.endComponent()
					.build();
	
	List<ComponentEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(ComponentEffect effect);
	
	void removeEffect(ComponentEffect effect);

	DeathProtectionComponent clone();
	
	@Override
	default NBTCodec<? extends DeathProtectionComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
