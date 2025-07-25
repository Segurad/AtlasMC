package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class InterfacedEnumStringField<T, K extends Enum<?> & EnumName> extends NBTField<T> {

	private final Function<T, ? super K> get;
	private final BiConsumer<T, ? super K> set;
	private final Function<String, K> enumSupplier;
	private final K defaultValue;
	
	public InterfacedEnumStringField(CharSequence key, Function<T, ? super K> get, BiConsumer<T, ? super K> set, Function<String, K> enumSupplier, K defaultValue) {
		super(key, STRING, true);
		this.get = get;
		this.set = set;
		this.enumSupplier = enumSupplier;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Object raw = get.apply(value);
		if (useDefault && raw == defaultValue)
			return true;
		if (!(raw instanceof EnumName v))
			return false;
		writer.writeStringTag(key, v.getName());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		K v = enumSupplier.apply(reader.readStringTag());
		set.accept(value, v);
	}

}
