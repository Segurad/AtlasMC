package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;

public interface JukeboxPlayableComponent extends ItemComponent {
	
	public static final NBTCodec<JukeboxPlayableComponent> 
	NBT_HANDLER = NBTCodec
					.builder(JukeboxPlayableComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.JUKEBOX_PLAYABLE.getNamespacedKey(), JukeboxPlayableComponent::getSong, JukeboxPlayableComponent::setSong, NamespacedKey.NBT_CODEC)
					.build();
	
	NamespacedKey getSong();
	
	void setSong(NamespacedKey song);
	
	@Override
	default NBTCodec<JukeboxPlayableComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	JukeboxPlayableComponent clone();

}
