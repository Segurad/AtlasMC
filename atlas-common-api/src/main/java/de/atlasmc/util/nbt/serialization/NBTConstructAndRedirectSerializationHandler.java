package de.atlasmc.util.nbt.serialization;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.constructor.Constructor;

public class NBTConstructAndRedirectSerializationHandler<T extends NBTSerializable> implements NBTSerializationHandler<T> {

	private final Constructor<T> constructor;
	
	public NBTConstructAndRedirectSerializationHandler(Constructor<T> constructor) {
		this.constructor = constructor;
	}
	
	@Override
	public void serialize(T value, NBTWriter ouput, NBTSerializationContext context) throws IOException {
		@SuppressWarnings("unchecked")
		NBTSerializationHandler<T> handler = (NBTSerializationHandler<T>) value.getNBTHandler();
		handler.serialize(value, ouput, context);
	}

	@Override
	public T deserialize(NBTReader input, NBTSerializationContext context) throws IOException {
		T value = constructor.invoke(input);
		@SuppressWarnings("unchecked")
		NBTSerializationHandler<T> handler = (NBTSerializationHandler<T>) value.getNBTHandler();
		return handler.deserialize(input, context);
	}

}
