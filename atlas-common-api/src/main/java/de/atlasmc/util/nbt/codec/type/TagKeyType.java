package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.NamespacedKey;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class TagKeyType<V> extends ObjectType<TagKey<V>> {

	private static final TagKeyType<?> INSTANCE = new TagKeyType<>();
	
	@SuppressWarnings("unchecked")
	public static <T> TagKeyType<T> getInstance() {
		return (TagKeyType<T>) INSTANCE;
	}
	
	private TagKeyType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, TagKey<V> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeStringTag(key, "#" + value.getNamespace());
		return true;
	}

	@Override
	public TagKey<V> deserialize(TagKey<V> key, NBTReader reader, CodecContext context) throws IOException {
		String v = reader.readStringTag();
		if (v.charAt(0) != '#')
			throw new NBTException("Tag identifier has to start with #: " + v);
		return new TagKey<>(NamespacedKey.of(v.substring(1)));
	}

	@Override
	public List<de.atlasmc.util.nbt.TagType> getTypes() {
		return STRING;
	}

}
