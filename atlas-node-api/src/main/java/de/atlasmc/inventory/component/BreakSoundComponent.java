package de.atlasmc.inventory.component;

import de.atlasmc.sound.Sound;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BreakSoundComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<BreakSoundComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BreakSoundComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.addField(Sound.getNBTSoundField(ComponentType.BREAK_SOUND, BreakSoundComponent::getSound, BreakSoundComponent::setSound, null))
					.build();
	
	Sound getSound();
	
	void setSound(Sound sound);
	
	@Override
	default NBTSerializationHandler<? extends BreakSoundComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
