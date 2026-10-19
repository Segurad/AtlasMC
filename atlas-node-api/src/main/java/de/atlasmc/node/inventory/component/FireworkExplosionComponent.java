package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.util.annotation.NotNull;

public interface FireworkExplosionComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<FireworkExplosionComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(FireworkExplosionComponent.class, FireworkExplosion.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(FireworkExplosionComponent::getExplosion)
				.setter(FireworkExplosionComponent::setExplosion)
				.build();
	
	@NotNull
	public static final StreamCodec<FireworkExplosionComponent>
	STREAM_CODEC = StreamCodec
					.builder(FireworkExplosionComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(FireworkExplosionComponent::getExplosion, FireworkExplosionComponent::setExplosion, FireworkExplosion.STREAM_CODEC)
					.build();
	
	FireworkExplosion getExplosion();
	
	void setExplosion(FireworkExplosion effect);
	
	@Override
	FireworkExplosionComponent clone();
	
	@Override
	default NBTCodec<? extends FireworkExplosionComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends FireworkExplosionComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
