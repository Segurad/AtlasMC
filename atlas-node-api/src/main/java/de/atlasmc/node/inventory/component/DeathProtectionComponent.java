package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.component.effect.ComponentEffect;
import de.atlasmc.util.annotation.NotNull;

public interface DeathProtectionComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<DeathProtectionComponent>
	NBT_CODEC = NBTCodec
					.builder(DeathProtectionComponent.class)
					.beginComponent(ComponentType.DEATH_PROTECTION.getNamespacedKey())
					.codecList("death_effects", DeathProtectionComponent::hasEffects, DeathProtectionComponent::getEffects, ComponentEffect.NBT_CODEC)
					.endComponent()
					.build();
	
	@NotNull
	public static final StreamCodec<DeathProtectionComponent>
	STREAM_CODEC = StreamCodec
					.builder(DeathProtectionComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.listCodec(DeathProtectionComponent::hasEffects, DeathProtectionComponent::getEffects, ComponentEffect.STREAM_CODEC)
					.build();
	
	List<ComponentEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(ComponentEffect effect);
	
	void removeEffect(ComponentEffect effect);

	@Override
	DeathProtectionComponent clone();
	
	@Override
	default NBTCodec<? extends DeathProtectionComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends DeathProtectionComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
