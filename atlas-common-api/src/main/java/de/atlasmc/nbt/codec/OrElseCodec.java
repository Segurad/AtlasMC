package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class OrElseCodec<T> implements NBTCodec<T> {

	private final List<TagType> tags;
	private final Class<?> clazz;
	private final NBTCodec<T> a;
	private final NBTCodec<T> b;
	
	@SuppressWarnings("unchecked")
	public OrElseCodec(Class<T> clazz, NBTCodec<? extends T> a, NBTCodec<? extends T> b) {
		this.clazz = Objects.requireNonNull(clazz);
		this.a = (NBTCodec<T>) Objects.requireNonNull(a);
		this.b = (NBTCodec<T>) Objects.requireNonNull(b);
		var list = new ArrayList<>(a.getTags());
		for (var tag : b.getTags()) {
			if (!list.contains(tag))
				list.add(tag);
		}
		tags = List.copyOf(list);
	}
	
	@Override
	public Class<?> getType() {
		return clazz;
	}

	@Override
	public T deserialize(T value, NBTReader input, CodecContext context) throws IOException {
		final var tag = input.getType();
		if (a.getTags().contains(tag)) {
			if (a.isField()) {
				return a.deserialize(input, context);
			} else {
				input.readNextEntry();
				return a.deserialize(input);
			}
		}
		if (b.isField()) {
			return b.deserialize(input, context);
		} else {
			input.readNextEntry();
			return b.deserialize(input);
		}
	}

	@Override
	public boolean serialize(CharSequence key, T value, NBTWriter output, CodecContext context) throws IOException {
		if (a.getType().isInstance(value)) {
			if (a.isField()) {
				return a.serialize(key, value, output, context);
			} else {
				output.writeCompoundTag(key);
				a.serialize(value, output, context);
				output.writeEndTag();
				return true;
			}
		} else if (b.getType().isInstance(value)) {
			if (b.isField()) {
				return b.serialize(key, value, output, context);
			} else {
				output.writeCompoundTag(key);
				b.serialize(value, output, context);
				output.writeEndTag();
				return true;
			}
		}
		return false;
	}

	@Override
	public List<TagType> getTags() {
		return tags;
	}

}
