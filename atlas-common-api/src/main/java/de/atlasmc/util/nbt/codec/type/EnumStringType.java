package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class EnumStringType<V, E extends Enum<E> & EnumName> extends AbstractEnumType<V, E> {

	public EnumStringType(Class<E> clazz) {
		super(clazz);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		if (!clazz.isInstance(value))
			return false;
		@SuppressWarnings("unchecked")
		E enumValue = (E) value;
		writer.writeStringTag(key, enumValue.getName());
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		@SuppressWarnings("unchecked")
		V v = (V) EnumUtil.getByName(clazz, reader.readStringTag());
		return v;
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
