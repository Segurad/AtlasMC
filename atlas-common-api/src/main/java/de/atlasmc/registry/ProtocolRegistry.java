package de.atlasmc.registry;

import java.io.IOException;
import java.util.function.ToIntFunction;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public interface ProtocolRegistry<T> extends Registry<T> {
	
	T getByID(int id);
	
	RegistryEntry<T> getEntryByID(int id);
	
	void setIDSupplier(ToIntFunction<T> idSupplier);
	
	ToIntFunction<T> getIDSupplier();
	
	void setDeserializer(Deserializer<T> deserializer);
	
	void setSerializer(Serializer<T> serializer);
	
	Deserializer<T> getDeserializer();
	
	Serializer<T> getSerializer();

	@FunctionalInterface
	public static interface Deserializer<T> {
		
		T deserialize(int id, ByteBuf buf, NBTReader reader) throws IOException;
		
	}
	
	@FunctionalInterface
	public static interface Serializer<T> {
		
		void serialize(T data, ByteBuf buf, NBTWriter writer) throws IOException;
		
	}
	
}
