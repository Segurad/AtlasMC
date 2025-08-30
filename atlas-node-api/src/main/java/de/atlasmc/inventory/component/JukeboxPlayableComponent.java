package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface JukeboxPlayableComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<JukeboxPlayableComponent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(JukeboxPlayableComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKey(ComponentType.JUKEBOX_PLAYABLE.getNamespacedKey(), JukeboxPlayableComponent::getSong, JukeboxPlayableComponent::setSong)
					.build();
	
	NamespacedKey getSong();
	
	void setSong(NamespacedKey song);
	
	@Override
	default NBTSerializationHandler<JukeboxPlayableComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	JukeboxPlayableComponent clone();

}
