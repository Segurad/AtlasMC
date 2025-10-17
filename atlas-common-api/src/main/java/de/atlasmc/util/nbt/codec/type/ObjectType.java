package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class ObjectType<V> extends FieldType {

	public abstract boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException;
	
	public abstract V deserialize(V value, NBTReader reader, CodecContext context) throws IOException;
	
}
