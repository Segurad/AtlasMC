package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface FireworksComponent extends ItemComponent {

	public static final NBTSerializationHandler<FireworksComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(FireworksComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.FIREWORKS.getNamespacedKey())
					.typeList("explosion", FireworksComponent::hasExplosions, FireworksComponent::getExplosions, FireworkExplosion.NBT_HANDLER)
					.byteField("flight_duration", FireworksComponent::getFlightDuration, FireworksComponent::setFlightDuration, (byte) 1)
					.endComponent()
					.build();
	
	List<FireworkExplosion> getExplosions();
	
	boolean hasExplosions();
	
	void addExplosion(FireworkExplosion explosion);
	
	void removeExplosions(FireworkExplosion explosion);
	
	int getFlightDuration();
	
	void setFlightDuration(int duration);
	
	FireworksComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends FireworksComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
