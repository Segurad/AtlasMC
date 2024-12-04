package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface NoteBlockSoundComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:note_block_sound");
	
	NamespacedKey getSound();
	
	void setSound(NamespacedKey sound);
	
	NoteBlockSoundComponent clone();

}
