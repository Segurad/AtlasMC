package de.atlasmc.node.inventory.component;

import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BreakSoundComponent extends ItemComponent {
	
	public static final NBTCodec<BreakSoundComponent>
	NBT_HANDLER = NBTCodec
					.builder(BreakSoundComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.enumStringOrType(ComponentType.BREAK_SOUND.getNamespacedKey(), BreakSoundComponent::getSound, BreakSoundComponent::setSound, EnumSound.class, ResourceSound.NBT_CODEC)
					.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	@Override
	default NBTCodec<? extends BreakSoundComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
