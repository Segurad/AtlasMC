package de.atlasmc.nbt.codec.field;

import java.io.IOException;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class CodecCollectionField<T, V> extends AbstractCollectionField<T, V, NBTCodec<V>> {

	public CodecCollectionField(CodecCollectionFieldBuilder<T, V> builder) {
		super(builder);
	}

	@Override
	public boolean serialize(T type, NBTWriter writer, CodecContext context) throws IOException {
		if ((serverOnly && context.clientSide) || !hasData.applyAsBoolean(type))
			return true;
		V value = getter.apply(type);
		return fieldType.serialize(key, value, writer, context);
	}

	@Override
	public void deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
		if (serverOnly && context.clientSide)
			return; // do not deserialize fields send by client that are server only
		fieldType.deserialize(getter.apply(type), reader, context);
	}

}
