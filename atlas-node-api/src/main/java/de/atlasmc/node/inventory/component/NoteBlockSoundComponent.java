package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.util.enums.EnumUtil;

public interface NoteBlockSoundComponent extends ItemComponent {
	
	public static final NBTCodec<NoteBlockSoundComponent>
	NBT_HANDLER = NBTCodec
					.builder(NoteBlockSoundComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.NOTE_BLOCK_SOUND.getNamespacedKey(), NoteBlockSoundComponent::getSound, NoteBlockSoundComponent::setSound, EnumUtil.enumStringNBTCodec(EnumSound.class))
					.build();
					
	EnumSound getSound();
	
	void setSound(EnumSound sound);
	
	NoteBlockSoundComponent clone();
	
	@Override
	default NBTCodec<? extends NoteBlockSoundComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
