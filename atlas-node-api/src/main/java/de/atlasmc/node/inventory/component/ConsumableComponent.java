package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.IDHolder;
import de.atlasmc.node.inventory.component.effect.ComponentEffect;
import de.atlasmc.node.sound.EnumSound;
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
					.addField(Sound.getNBTSoundField("sound", ConsumableComponent::getSound, ConsumableComponent::setSound, EnumSound.ENTITY_GENERIC_EAT))
					.boolField("has_consume_particles", ConsumableComponent::hasParticles, ConsumableComponent::setParticles)
					.typeList("on_consume_effects", ConsumableComponent::hasEffects, ConsumableComponent::getEffects, ComponentEffect.NBT_HANDLER)
					.endComponent()
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
