package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.sound.EnumSound;

public interface NoteBlockSoundComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:note_block_sound");
	
	EnumSound getSound();
	
	void setSound(EnumSound sound);
	
	NoteBlockSoundComponent clone();

}
