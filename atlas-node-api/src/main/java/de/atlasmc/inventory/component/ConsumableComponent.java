package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.sound.Sound;

public interface ConsumableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:consumable");
	
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
