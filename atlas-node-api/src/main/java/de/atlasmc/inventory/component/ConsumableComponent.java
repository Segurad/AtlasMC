package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.effect.ComponentEffect;

public interface ConsumableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:consumable");
	
	float getConsumeSeconds();
	
	void setConsumeSeconds(float duration);
	
	Animation getAnimation();
	
	void setAnimation(Animation animation);
	
	NamespacedKey getSound();
	
	void setSound(NamespacedKey sound);
	
	/**
	 * Returns the sound range as float.
	 * Will be {@link Float#NaN} if not set.
	 * @return range
	 */
	float getSoundRange();
	
	void setSoundRange(float range);
	
	boolean hasParticles();
	
	void setParticles(boolean particles);
	
	List<ComponentEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(ComponentEffect effect);
	
	void removeEffect(ComponentEffect effect);
	
	ConsumableComponent clone();
	
	public static enum Animation {
		
		NONE,
		EAT,
		DRINK,
		BLOCK,
		BOW,
		SPEAR,
		CROSSBOW,
		SPYGLASS,
		TOOT_HORN;

		private final String name;
		
		private Animation() {
			name = name().toLowerCase();
		}
		
		public String getName() {
			return name;
		}
		
	}

}
