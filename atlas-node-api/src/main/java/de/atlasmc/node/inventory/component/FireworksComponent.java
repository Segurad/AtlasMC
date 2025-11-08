package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.FireworkExplosion;

public interface FireworksComponent extends ItemComponent {

	public static final NBTCodec<FireworksComponent>
	NBT_HANDLER = NBTCodec
					.builder(FireworksComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.FIREWORKS.getNamespacedKey())
					.codecList("explosion", FireworksComponent::hasExplosions, FireworksComponent::getExplosions, FireworkExplosion.NBT_HANDLER)
					.byteField("flight_duration", FireworksComponent::getFlightDuration, FireworksComponent::setFlightDuration, (byte) 1)
					.endComponent()
					.build();
	
	public static final StreamCodec<FireworksComponent>
	STREAM_CODEC = StreamCodec
					.builder(FireworksComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varInt(FireworksComponent::getFlightDuration, FireworksComponent::setFlightDuration)
					.listCodec(FireworksComponent::hasExplosions, FireworksComponent::getExplosions, FireworkExplosion.STREAM_CODEC)
					.build();
	
	List<FireworkExplosion> getExplosions();
	
	boolean hasExplosions();
	
	void addExplosion(FireworkExplosion explosion);
	
	void removeExplosions(FireworkExplosion explosion);
	
	int getFlightDuration();
	
	void setFlightDuration(int duration);
	
	FireworksComponent clone();
	
	@Override
	default NBTCodec<? extends FireworksComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends FireworksComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
