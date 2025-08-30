package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ConsumableComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<ConsumableComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ConsumableComponent.class)
					.beginComponent(ComponentType.CONSUMABLE.getNamespacedKey())
					.floatField("consume_seconds", ConsumableComponent::getConsumeSeconds, ConsumableComponent::setConsumeSeconds, 1.6f)
					.enumStringField("animation", ConsumableComponent::getAnimation, ConsumableComponent::setAnimation, Animation::getByName, Animation.EAT)
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
	
	public static enum Animation implements AtlasEnum {
		
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

		private static List<Animation> VALUES;
		
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
		
		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static Animation getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Animation> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Animation value = values.get(i);
				if (value.name.equals(name)) 
					return value;
			}
			return null;
		}
		
		public static Animation getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Animation> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
