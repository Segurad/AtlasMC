package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PlaySound extends ComponentEffect {
	
	public static final NBTSerializationHandler<PlaySound>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PlaySound.class)
					.include(ComponentEffect.NBT_HANDLER)
					.addField(Sound.getNBTSoundField("sound", PlaySound::getSound, PlaySound::setSound, null))
					.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	PlaySound clone();
	
	@Override
	default NBTSerializationHandler<? extends PlaySound> getNBTHandler() {
		return NBT_HANDLER;
	}

}
