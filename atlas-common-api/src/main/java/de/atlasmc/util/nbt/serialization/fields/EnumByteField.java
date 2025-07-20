package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class EnumByteField<T, K extends Enum<?>> extends AbstractObjectField<T, K> {

	private final IntFunction<K> enumSupplier;
	private final ToIntFunction<K> idSupplier;
	private final K defaultValue;
	
	public EnumByteField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, IntFunction<K> enumSupplier, ToIntFunction<K> idSupplier, K defaultValue) {
		super(key, BYTE, get, set, true);
		this.enumSupplier = enumSupplier;
		this.defaultValue = defaultValue;
		this.idSupplier = idSupplier;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = get.apply(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeByteTag(key, idSupplier.applyAsInt(v));
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		K v = enumSupplier.apply(reader.readByteTag());
		set.accept(value, v);
	}

}
