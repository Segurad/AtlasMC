package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ReuseableObjectField<T, V> extends AbstractCollectionField<T, V, V> {

	public ReuseableObjectField(ReuseableObjectFieldBuilder<T, V> builder) {
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
