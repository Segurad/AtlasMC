package de.atlasmc.util.nbt.serialization;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTWriter;

public interface NBTSerializable {
	
	NBTSerializationHandler<? extends NBTSerializable> getNBTHandler();
	
	default void writeToNBT(NBTWriter writer, NBTSerializationContext context) throws IOException {
		@SuppressWarnings("unchecked")
		NBTSerializationHandler<NBTSerializable> handler = (NBTSerializationHandler<NBTSerializable>) getNBTHandler();
		handler.serialize(this, writer, context);
	}

}
