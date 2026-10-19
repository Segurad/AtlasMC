package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.annotation.NotNull;

public interface BreakSoundComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<BreakSoundComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(BreakSoundComponent.class, Sound.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(BreakSoundComponent::getSound)
				.setter(BreakSoundComponent::setSound)
				.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	@Override
	default NBTCodec<? extends BreakSoundComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
