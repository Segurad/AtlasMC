package de.atlasmc.nbt.io;

import java.io.IOException;

@FunctionalInterface
public interface NBTReaderFunction<T> {
	
	T fromNBT(NBTReader reader) throws IOException;

}
