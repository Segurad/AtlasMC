package de.atlasmc.util.nbt.serialization;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.serialization.SerializationHandler;

public interface NBTSerializationHandler<T> extends SerializationHandler<T, NBTReader, NBTWriter, NBTSerializationContext> {
	
	Class<? extends T> getType();
	
	@Override
	default NBTSerializationContext getDefaultContext() {
		return NBTSerializationContext.DEFAULT_SERVER;
	}
	
	public static <T> NBTSerializationHandlerBuilder<T> builder(Class<T> clazz) {
		return new NBTSerializationHandlerBuilder<>(clazz);
	}

}
