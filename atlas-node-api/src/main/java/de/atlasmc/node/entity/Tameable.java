package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Tameable extends Animal {
	
	public static final NBTSerializationHandler<Tameable>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Tameable.class)
					.include(Animal.NBT_HANDLER)
					.uuid("Owner", Tameable::getOwner, Tameable::setOwner)
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
	default NBTSerializationHandler<? extends Tameable> getNBTHandler() {
		return NBT_HANDLER;
	}

}
