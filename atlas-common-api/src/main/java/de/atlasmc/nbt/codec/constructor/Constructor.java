package de.atlasmc.nbt.codec.constructor;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public interface Constructor<T> {
	
	T construct(NBTReader reader, CodecContext context) throws IOException;
	
	void serialize(T value, NBTWriter writer, CodecContext context) throws IOException;

	/**
	 * Whether or not the codec must be a field in order to be constructed
	 * @return true if requires
	 */
	boolean requiredField();
	
}
