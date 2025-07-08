package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class TagField<T, K> extends AbstractObjectField<T, Tag<K>> {

	public TagField(CharSequence key, Function<T, Tag<K>> get, BiConsumer<T, Tag<K>> set) {
		super(key, STRING, get, set);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Tag<K> tag = get.apply(value);
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
		Tag<K> tag = Tags.getTag(NamespacedKey.of(v.substring(1)));
		set.accept(value, tag);
	}

}
