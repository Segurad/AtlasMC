package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class TagField<T, K> extends AbstractObjectField<T, Tag<K>> {

	public TagField(CharSequence key, Function<T, Tag<K>> supplier, BiConsumer<T, Tag<K>> consumer) {
		super(key, TagType.STRING, supplier, consumer);
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Tag<K> tag = supplier.apply(value);
		if (tag == null)
			return;
		writer.writeStringTag(key, "#" + tag.getNamespace());
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		String v = reader.readStringTag();
		if (v.charAt(0) != '#')
			return;
		Tag<K> tag = Tags.getTag(NamespacedKey.of(v.substring(1)));
		consumer.accept(value, tag);
	}

}
