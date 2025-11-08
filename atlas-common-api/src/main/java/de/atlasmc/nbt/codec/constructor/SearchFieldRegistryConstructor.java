package de.atlasmc.nbt.codec.constructor;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;

public class SearchFieldRegistryConstructor<T, K extends Namespaced> extends AbstractRegistryConstructor<T, K> {

	private final CharKey keyField;
	private final Function<T, K> keyReverseSupplier;
	
	public SearchFieldRegistryConstructor(CharSequence keyField, RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		super(registry, constructor);
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
		K regValue = registry.getValue(key);
		return constructor.apply(regValue);
	}

	@Override
	public void serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (keyReverseSupplier == null)
			return;
		K key = keyReverseSupplier.apply(value);
		if (key == null)
			return;
		NamespacedKey nkey = context.clientSide ? key.getClientKey() : key.getNamespacedKey();
		writer.writeStringTag(keyField, nkey.toString());
	}

}
