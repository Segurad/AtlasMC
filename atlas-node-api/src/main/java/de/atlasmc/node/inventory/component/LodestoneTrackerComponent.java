package de.atlasmc.node.inventory.component;

import org.joml.Vector3i;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public interface LodestoneTrackerComponent extends ItemComponent {
	
	public static final NBTCodec<LodestoneTrackerComponent>
	NBT_HANDLER = NBTCodec
					.builder(LodestoneTrackerComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.LODESTONE_TRACKER.getNamespacedKey())
					.beginComponent("target", LodestoneTrackerComponent::hasTarget)
					.codec("pos", LodestoneTrackerComponent::getLocation, LodestoneTrackerComponent::setLocation, NBTCodecs.VECTOR_3I)
					.codec("dimension", LodestoneTrackerComponent::getDimension, LodestoneTrackerComponent::setDimension, NamespacedKey.NBT_CODEC)
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
