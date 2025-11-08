package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public interface Tameable extends Animal {
	
	public static final NBTCodec<Tameable>
	NBT_HANDLER = NBTCodec
					.builder(Tameable.class)
					.include(Animal.NBT_CODEC)
					.codec("Owner", Tameable::getOwner, Tameable::setOwner, NBTCodecs.UUID_CODEC)
					.boolField("Sitting", Tameable::isSitting, Tameable::setSitting, false)
					.boolField("Tame", Tameable::isTamed, Tameable::setTamed, false) // non standard
					.build();
	
	boolean isSitting();
	
	void setSitting(boolean sitting);
	
	boolean isTamed();
	
	void setTamed(boolean tamed);
	
	UUID getOwner();
	
	void setOwner(UUID owner);
	
	@Override
	default NBTCodec<? extends Tameable> getNBTCodec() {
		return NBT_HANDLER;
	}

}
