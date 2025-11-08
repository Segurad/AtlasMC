package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public interface Phantom extends FlyingMob {
	
	public static final NBTCodec<Phantom>
	NBT_HANDLER = NBTCodec
					.builder(Phantom.class)
					.include(FlyingMob.NBT_HANDLER)
					.intField("size", Phantom::getSize, Phantom::setSize, 0)
					.codec("anchor_pos", Phantom::getAnchorUnsafe, Phantom::setAnchor, NBTCodecs.VECTOR_3I)
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
	default NBTCodec<? extends Phantom> getNBTCodec() {
		return NBT_HANDLER;
	}

}
