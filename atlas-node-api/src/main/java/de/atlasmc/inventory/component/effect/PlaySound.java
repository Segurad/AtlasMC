package de.atlasmc.inventory.component.effect;

import de.atlasmc.sound.Sound;

public interface PlaySound extends ComponentEffect {
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	@Override
	default ComponentEffectType getType() {
		return ComponentEffectType.PLAY_SOUND;
	}

}
