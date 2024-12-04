package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:instrument");
	
	NamespacedKey getSound();
	
	void setSound(NamespacedKey sound);
	
	float getSoundRange();
	
	void setSoundRange(float range);
	
	int getUseDuration();
	
	void setUseDuration(int duration);
	
	InstrumentComponent clone();

}
