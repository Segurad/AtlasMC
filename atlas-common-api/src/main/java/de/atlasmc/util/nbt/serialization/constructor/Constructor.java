package de.atlasmc.util.nbt.serialization.constructor;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public interface Constructor<T> {
	
	public T construct(NBTReader reader, NBTSerializationContext context) throws IOException;
	
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException;

}
