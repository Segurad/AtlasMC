package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.IDHolder;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.inventory.component.effect.ComponentEffect;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ConsumableComponent extends ItemComponent {
	
	public static final NBTCodec<ConsumableComponent>
	NBT_HANDLER = NBTCodec
					.builder(ConsumableComponent.class)
					.beginComponent(ComponentType.CONSUMABLE.getNamespacedKey())
					.floatField("consume_seconds", ConsumableComponent::getConsumeSeconds, ConsumableComponent::setConsumeSeconds, 1.6f)
					.enumStringField("animation", ConsumableComponent::getAnimation, ConsumableComponent::setAnimation, Animation.class, Animation.EAT)
					.enumStringOrType("sound", ConsumableComponent::getSound, ConsumableComponent::setSound, EnumSound.class, ResourceSound.NBT_CODEC, EnumSound.ENTITY_GENERIC_EAT)
					.boolField("has_consume_particles", ConsumableComponent::hasParticles, ConsumableComponent::setParticles)
					.typeList("on_consume_effects", ConsumableComponent::hasEffects, ConsumableComponent::getEffects, ComponentEffect.NBT_HANDLER)
					.endComponent()
					.build();
	
	public static final StreamCodec<ConsumableComponent>
	STREAM_CODEC = StreamCodec
					.builder(ConsumableComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.floatValue(ConsumableComponent::getConsumeSeconds, ConsumableComponent::setConsumeSeconds)
					.varIntEnum(ConsumableComponent::getAnimation, ConsumableComponent::setAnimation, Animation.class)
					.enumValueOrCodec(ConsumableComponent::getSound, ConsumableComponent::setSound, EnumSound.class, ResourceSound.STREAM_CODEC)
					.booleanValue(ConsumableComponent::hasParticles, ConsumableComponent::setParticles)
					.listCodec(ConsumableComponent::hasEffects, ConsumableComponent::getEffects, ComponentEffect.STREAM_CODEC)
					.build();
	
	float getConsumeSeconds();
	
	void setConsumeSeconds(float duration);
	
	Animation getAnimation();
	
	void setAnimation(Animation animation);
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	boolean hasParticles();
	
	void setParticles(boolean particles);
	
	List<ComponentEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(ComponentEffect effect);
	
	void removeEffect(ComponentEffect effect);
	
	ConsumableComponent clone();
	
	public static enum Animation implements EnumName, IDHolder {
		
		NONE,
		EAT,
		DRINK,
		BLOCK,
		BOW,
		SPEAR,
		CROSSBOW,
		SPYGLASS,
		TOOT_HORN,
		BRUSH;

		private final String name;
		
		private Animation() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
