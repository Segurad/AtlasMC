package de.atlasmc.node.inventory.component;

import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface NoteBlockSoundComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<NoteBlockSoundComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(NoteBlockSoundComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.enumStringField(ComponentType.NOTE_BLOCK_SOUND.getNamespacedKey(), NoteBlockSoundComponent::getSound, NoteBlockSoundComponent::setSound, EnumSound::getByName, null)
					.build();
					
	EnumSound getSound();
	
	void setSound(EnumSound sound);
	
	NoteBlockSoundComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends NoteBlockSoundComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
