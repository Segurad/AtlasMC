package de.atlasmc.util.nbt.codec;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface NBTSerializable {
	
	NBTCodec<? extends NBTSerializable> getNBTCodec();
	
	default void writeToNBT(NBTWriter writer, CodecContext context) throws IOException {
		@SuppressWarnings("unchecked")
		NBTCodec<NBTSerializable> handler = (NBTCodec<NBTSerializable>) getNBTCodec();
		handler.serialize(this, writer, context);
	}

}
