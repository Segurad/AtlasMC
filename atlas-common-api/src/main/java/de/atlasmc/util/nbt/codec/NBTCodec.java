package de.atlasmc.util.nbt.codec;

import de.atlasmc.util.codec.Codec;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface NBTCodec<T> extends Codec<T, NBTReader, NBTWriter, CodecContext> {
	
	Class<? extends T> getType();
	
	@Override
	default CodecContext getDefaultContext() {
		return CodecContext.DEFAULT_SERVER;
	}
	
	public static <T> NBTCodecBuilder<T> builder(Class<T> clazz) {
		return new NBTCodecBuilder<>(clazz);
	}

}
