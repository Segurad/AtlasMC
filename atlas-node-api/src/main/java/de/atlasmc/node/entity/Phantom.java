package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Phantom extends FlyingMob {
	
	public static final NBTSerializationHandler<Phantom>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Phantom.class)
					.include(FlyingMob.NBT_HANDLER)
					.intField("size", Phantom::getSize, Phantom::setSize, 0)
					.vector3i("anchor_pos", Phantom::getAnchorUnsafe, Phantom::setAnchor)
					.build();
	
	int getSize();
	
	void setSize(int size);
	
	Vector3i getAnchorUnsafe();
	
	default Vector3i getAnchor() {
		return getAnchor(new Vector3i());
	}
	
	Vector3i getAnchor(Vector3i pos);
	
	void setAnchor(Vector3i pos);
	
	@Override
	default NBTSerializationHandler<? extends Phantom> getNBTHandler() {
		return NBT_HANDLER;
	}

}
