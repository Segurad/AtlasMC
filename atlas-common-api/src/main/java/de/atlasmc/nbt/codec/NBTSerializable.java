package de.atlasmc.nbt.codec;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public interface NBTSerializable {
	
	NBTCodec<? extends NBTSerializable> getNBTCodec();
	
	default void writeToNBT(NBTWriter writer, CodecContext context) throws IOException {
		@SuppressWarnings("unchecked")
		NBTCodec<NBTSerializable> codec = (NBTCodec<NBTSerializable>) getNBTCodec();
		if (codec.isField()) {
			codec.serialize(this, writer, context);
		} else {
			writer.writeCompoundTag();
			codec.serialize(this, writer, context);
			writer.writeEndTag();
		}
	}

}
