package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class InterfacedEnumStringType<V, E extends Enum<E> & EnumName> extends ObjectType<V> {

	private final Class<E> clazz;
	
	public InterfacedEnumStringType(Class<E> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		if (!clazz.isInstance(value))
			return false;
		writer.writeStringTag(key, ((EnumName) value).getName());
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
