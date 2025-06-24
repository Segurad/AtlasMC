package de.atlasmc.util.nbt.serialization.constructor;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class SearchFieldRegistryConstructor<T, K extends Namespaced> extends AbstractRegistryConstructor<T, K> {

	private final CharKey keyField;
	private final Function<T, K> keyReverseSupplier;
	
	public SearchFieldRegistryConstructor(CharSequence keyField, Registry<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		super(registry, constructor);
		this.keyField = CharKey.literal(keyField);
		this.keyReverseSupplier = keyReverseSupplier;
	}

	@Override
	public T construct(NBTReader reader, NBTSerializationContext context) throws IOException {
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
		K regValue = registry.get(key);
		return constructor.apply(regValue);
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (keyReverseSupplier == null)
			return;
		K key = keyReverseSupplier.apply(value);
		if (key == null)
			return;
		NamespacedKey nkey = context.clientSide ? key.getClientKey() : key.getNamespacedKey();
		writer.writeStringTag(keyField, nkey.toString());
	}

}
