package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.FireworkExplosion;

public interface FireworkExplosionComponent extends ItemComponent {
	
	public static final NBTCodec<FireworkExplosionComponent>
	NBT_HANDLER = NBTCodec
					.builder(FireworkExplosionComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.FIREWORK_EXPLOSION.getNamespacedKey(), FireworkExplosionComponent::getExplosion, FireworkExplosionComponent::setExplosion, FireworkExplosion.NBT_HANDLER)
					.build();
	
	public static final StreamCodec<FireworkExplosionComponent>
	STREAM_CODEC = StreamCodec
					.builder(FireworkExplosionComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(FireworkExplosionComponent::getExplosion, FireworkExplosionComponent::setExplosion, FireworkExplosion.STREAM_CODEC)
					.build();
	
	FireworkExplosion getExplosion();
	
	void setExplosion(FireworkExplosion effect);
	
	FireworkExplosionComponent clone();
	
	@Override
	default NBTCodec<? extends FireworkExplosionComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends FireworkExplosionComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
