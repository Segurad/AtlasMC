package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.sound.Sound;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:instrument");
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	int getUseDuration();
	
	void setUseDuration(int duration);
	
	InstrumentComponent clone();

}
