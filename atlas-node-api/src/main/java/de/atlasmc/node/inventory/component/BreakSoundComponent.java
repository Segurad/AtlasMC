package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.annotation.NotNull;

public interface BreakSoundComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<BreakSoundComponent>
	NBT_HANDLER = NBTCodec
					.builder(BreakSoundComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.BREAK_SOUND.getNamespacedKey(), BreakSoundComponent::getSound, BreakSoundComponent::setSound, Sound.NBT_CODEC)
					.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	@Override
	default NBTCodec<? extends BreakSoundComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
