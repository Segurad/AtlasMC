package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class NamespacedKeyType extends ObjectType<NamespacedKey> {

	private static final NamespacedKeyType INSTANCE = new NamespacedKeyType();
	
	public static NamespacedKeyType getInstance() {
		return INSTANCE;
	}
	
	private NamespacedKeyType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, NamespacedKey value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeStringTag(value, value.toString());
		return true;
	}

	@Override
	public NamespacedKey deserialize(NamespacedKey value, NBTReader reader, CodecContext context) throws IOException {
		return NamespacedKey.of(reader.readStringTag());
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
