package de.atlasmc.node.inventory.component;

import org.joml.Vector3i;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface LodestoneTrackerComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<LodestoneTrackerComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(LodestoneTrackerComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.LODESTONE_TRACKER.getNamespacedKey())
					.beginComponent("target", LodestoneTrackerComponent::hasTarget)
					.vector3i("pos", LodestoneTrackerComponent::getLocation, LodestoneTrackerComponent::setLocation)
					.namespacedKey("dimension", LodestoneTrackerComponent::getDimension, LodestoneTrackerComponent::setDimension)
					.endComponent()
					.boolField("tracked", LodestoneTrackerComponent::isTracked, LodestoneTrackerComponent::setTracked, true)
					.endComponent()
					.build();
	
	boolean hasTarget();
	
	Vector3i getLocation();
	
	void setLocation(Vector3i location);
	
	NamespacedKey getDimension();
	
	void setDimension(NamespacedKey dimension);

	boolean isTracked();
	
	void setTracked(boolean tracked);
	
	LodestoneTrackerComponent clone();

}
