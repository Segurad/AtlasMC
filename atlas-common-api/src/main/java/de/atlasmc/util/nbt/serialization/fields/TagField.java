package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class TagField<T, K> extends AbstractObjectField<T, TagKey<K>> {

	public TagField(CharSequence key, Function<T, TagKey<K>> get, BiConsumer<T, TagKey<K>> set) {
		super(key, STRING, get, set);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		TagKey<K> tag = get.apply(value);
		if (tag == null)
			return true;
		writer.writeStringTag(key, "#" + tag.getNamespace());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		String v = reader.readStringTag();
		if (v.charAt(0) != '#')
			return;
		TagKey<K> tag = new TagKey<>(NamespacedKey.of(v.substring(1)));
		set.accept(value, tag);
	}

}
