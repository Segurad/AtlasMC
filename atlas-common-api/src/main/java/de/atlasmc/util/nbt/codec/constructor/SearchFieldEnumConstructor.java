package de.atlasmc.util.nbt.codec.constructor;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class SearchFieldEnumConstructor<T, K extends Enum<K> & EnumName> implements Constructor<T> {

	private final Class<K> clazz;
	private final Function<K, T> constructor;
	private final Function<T, K> keyReverseSupplier;
	private final CharKey keyField;
	
	public SearchFieldEnumConstructor(CharSequence keyField, Class<K> clazz, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.clazz = clazz;
		this.constructor = constructor;
		this.keyField = CharKey.literal(keyField);
		this.keyReverseSupplier = keyReverseSupplier;
	}

	@Override
	public T construct(NBTReader reader, CodecContext context) throws IOException {
		String key;
		if (!keyField.equals(reader.getFieldName())) {
			reader.mark();
			if (!reader.search(keyField, TagType.STRING))
				throw new NBTException("Key field not found: " + keyField);
			key = reader.readStringTag();
			reader.reset();
		} else {
			key = reader.readStringTag();
		}
		K regValue = EnumUtil.getByName(clazz, key);
		return constructor.apply(regValue);
	}

	@Override
	public void serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (keyReverseSupplier == null)
			return;
		K key = keyReverseSupplier.apply(value);
		if (key == null)
			return;
		writer.writeStringTag(keyField, key.getName());
	}

}
