package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface JukeboxPlayableComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<JukeboxPlayableComponent> 
	NBT_CODEC = CodecFieldCodec
				.builder(JukeboxPlayableComponent.class, NamespacedKey.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(JukeboxPlayableComponent::getSong)
				.setter(JukeboxPlayableComponent::setSong)
				.build();
	
	NamespacedKey getSong();
	
	void setSong(NamespacedKey song);
	
	@Override
	default NBTCodec<JukeboxPlayableComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	JukeboxPlayableComponent clone();

}
