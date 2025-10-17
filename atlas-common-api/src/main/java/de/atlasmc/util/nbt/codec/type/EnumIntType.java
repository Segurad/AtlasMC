package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class EnumIntType<V extends Enum<V> & IDHolder> extends AbstractEnumType<V> {

	public EnumIntType(Class<V> clazz) {
		super(clazz);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeIntTag(key, value.getID());
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		return EnumUtil.getByID(clazz, reader.readIntTag());
	}

	@Override
	public List<TagType> getTypes() {
		return INT;
	}

}
