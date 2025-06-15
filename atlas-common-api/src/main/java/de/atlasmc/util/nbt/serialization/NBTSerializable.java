package de.atlasmc.util.nbt.serialization;

public interface NBTSerializable {
	
	NBTSerializationHandler<? extends NBTSerializable> getNBTHandler();

}
