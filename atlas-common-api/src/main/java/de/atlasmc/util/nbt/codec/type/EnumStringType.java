package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class EnumStringType<V extends Enum<V> & EnumName> extends AbstractEnumType<V> {

	public EnumStringType(Class<V> clazz) {
		super(clazz);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeStringTag(key, value.getName());
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		return EnumUtil.getByName(clazz, reader.readStringTag());
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
