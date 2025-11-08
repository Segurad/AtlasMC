package de.atlasmc.nbt.io;

import java.io.IOException;

@FunctionalInterface
public interface NBTWriterFunction<T> {

	void toNBT(T value, NBTWriter writer, boolean systemData) throws IOException;
	
}
