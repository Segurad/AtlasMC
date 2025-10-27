package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.Builder;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class BuilderField<T> extends NBTField<T> implements Builder<NBTField<T>> {

	private final Builder<NBTField<T>> builder;
	
	public BuilderField(CharSequence key, List<TagType> types, boolean serverOnly, NBTCompoundFieldBuilder<T> builder) {
		super(key, types, serverOnly);
		this.builder = builder;
	}
	
	public Builder<NBTField<T>> getBuilder() {
		return builder;
	}

	@Override
	public NBTField<T> build() {
		return builder.build();
	}

	@Override
	public void clear() {
		builder.clear();
	}

	@Override
	public boolean serialize(T type, NBTWriter writer, CodecContext context) throws IOException {
		throw new UnsupportedOperationException("Builder");
	}

	@Override
	public void deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
		throw new UnsupportedOperationException("Builder");
	}
	
}
