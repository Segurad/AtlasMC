package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:instrument");
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	InstrumentComponent clone();

}
