package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface JukeboxPlayableComponent extends AbstractTooltipComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:jukebox_playable");
	
	NamespacedKey getSong();
	
	void setSong(NamespacedKey song);
	
	JukeboxPlayableComponent clone();

}
