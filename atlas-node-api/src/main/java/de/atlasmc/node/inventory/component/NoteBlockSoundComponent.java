package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public interface NoteBlockSoundComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<NoteBlockSoundComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(NoteBlockSoundComponent.class, EnumUtil.enumStringNBTCodec(EnumSound.class))
				.include(ItemComponent.NBT_CODEC)
				.getter(NoteBlockSoundComponent::getSound)
				.setter(NoteBlockSoundComponent::setSound)
				.build();
					
	EnumSound getSound();
	
	void setSound(EnumSound sound);
	
	@Override
	NoteBlockSoundComponent clone();
	
	@Override
	default NBTCodec<? extends NoteBlockSoundComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
