package de.atlasmc.nbt.codec.constructor;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public interface Constructor<T> {
	
	public T construct(NBTReader reader, CodecContext context) throws IOException;
	
	public void serialize(T value, NBTWriter writer, CodecContext context) throws IOException;

}
