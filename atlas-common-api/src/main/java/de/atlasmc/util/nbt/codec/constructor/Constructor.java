package de.atlasmc.util.nbt.codec.constructor;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface Constructor<T> {
	
	public T construct(NBTReader reader, CodecContext context) throws IOException;
	
	public void serialize(T value, NBTWriter writer, CodecContext context) throws IOException;

}
