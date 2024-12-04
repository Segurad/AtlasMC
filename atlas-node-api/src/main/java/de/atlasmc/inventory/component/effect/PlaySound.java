package de.atlasmc.inventory.component.effect;

import de.atlasmc.NamespacedKey;

public interface PlaySound extends ComponentEffect {
	
	NamespacedKey getSound();
	
	void setSound(NamespacedKey sound);
	
	/**
	 * Returns the sound range as float.
	 * Will be {@link Float#NaN} if not set.
	 * @return range
	 */
	float getSoundRange();
	
	void setSoundRange(float range);
	
	@Override
	default ConsumeEffectType getType() {
		return ConsumeEffectType.PLAY_SOUND;
	}

}
