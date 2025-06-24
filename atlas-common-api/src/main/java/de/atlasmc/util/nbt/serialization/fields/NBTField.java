package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public abstract class NBTField<T> {
	
	public final CharKey key;
	public final TagType type;
	public final boolean useDefault;
	
	public NBTField(CharSequence key, TagType type, boolean useDefault) {
		this.key = CharKey.literal(key);
		this.type = type;
		this.useDefault = useDefault;
	}
	
	public abstract boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException;
	
	public abstract void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException;
	
}
