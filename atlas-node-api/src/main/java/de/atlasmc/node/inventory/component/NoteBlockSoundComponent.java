package de.atlasmc.node.inventory.component;

import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface NoteBlockSoundComponent extends ItemComponent {
	
	public static final NBTCodec<NoteBlockSoundComponent>
	NBT_HANDLER = NBTCodec
					.builder(NoteBlockSoundComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.enumStringField(ComponentType.NOTE_BLOCK_SOUND.getNamespacedKey(), NoteBlockSoundComponent::getSound, NoteBlockSoundComponent::setSound, EnumSound.class, null)
					.build();
					
	EnumSound getSound();
	
	void setSound(EnumSound sound);
	
	NoteBlockSoundComponent clone();
	
	@Override
	default NBTCodec<? extends NoteBlockSoundComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
