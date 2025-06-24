package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class NamedEnumField<T, K extends Enum<?> & EnumName> extends AbstractObjectField<T, K> {

	private final Function<String, K> enumSupplier;
	private final K defaultValue;
	
	public NamedEnumField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> enumSupplier, K defaultValue) {
		super(key, TagType.STRING, get, set, true);
		this.enumSupplier = enumSupplier;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = get.apply(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeStringTag(key, v.getName());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		K v = enumSupplier.apply(reader.readStringTag());
		set.accept(value, v);
	}

}
