package de.atlasmc.chat;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.CloneException;
import de.atlasmc.util.OpenCloneable;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class Filterable<T> implements OpenCloneable {
	
	private T raw;
	private T filtered;
	
	public Filterable() {
		this(null, null);
	}
	
	public Filterable(T raw) {
		this(raw, null);
	}
	
	public Filterable(T raw, T filtered) {
		this.raw = raw;
		this.filtered = filtered;
	}
	
	public T getRaw() {
		return raw;
	}
	
	public void setRaw(T raw) {
		this.raw = raw;
	}
	
	public T getFiltered() {
		return filtered;
	}
	
	public void setFiltered(T filtered) {
		this.filtered = filtered;
	}
	
	public boolean hasFiltered() {
		return filtered != null;
	}
	
	public static <T> NBTCodec<Filterable<T>> filterableCodec(NBTCodec<T> codec) {
		return new FilterableNBTCodec<>(codec);
	}
	
	public static <T> StreamCodec<Filterable<T>> filterableCodec(StreamCodec<T> codec) {
		return new FilterableStreamCodec<>(codec);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Filterable<T> clone() {
		Filterable<T> clone;
		try {
			clone = (Filterable<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
		if (clone.raw instanceof OpenCloneable cloneable)
			clone.raw = (T) cloneable.clone();
		if (clone.filtered instanceof OpenCloneable cloneable)
			clone.filtered = (T) cloneable.clone();
		return clone;
	}
	
	private static class FilterableNBTCodec<T> implements NBTCodec<Filterable<T>> {

		private final NBTCodec<T> codec;
		
		public FilterableNBTCodec(NBTCodec<T> codec) {
			if (!codec.isField())
				throw new IllegalArgumentException("NBTCodec must be a field!");
			this.codec = codec;
		}
		
		@Override
		public Class<?> getType() {
			return Filterable.class;
		}

		@Override
		public boolean serialize(CharSequence key, Filterable<T> value, NBTWriter output, CodecContext context) throws IOException {
			if (value.hasFiltered()) {
				codec.serialize(key, value.raw, output, context);
			} else {
				output.writeCompoundTag(key);
				codec.serialize("raw", value.raw, output, context);
				codec.serialize("filtered", value.filtered, output, context);
				output.writeEndTag();
			}
			return true;
		}

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public Filterable<T> deserialize(Filterable<T> value, NBTReader input, CodecContext context) throws IOException {
			if (value == null)
				value = new Filterable<>();
			switch (input.getType()) {
			case STRING: {
				value.raw = codec.deserialize(null, input, context);
				break;
			}
			case COMPOUND: {
				input.readNextEntry();
				while (input.getType() != TagType.TAG_END) {
					if (input.getFieldName().equals("raw")) {
						value.raw = codec.deserialize(null, input, context);
					} else if (input.getFieldName().equals("filtered")) {
						value.filtered = codec.deserialize(null, input, context);
					} else {
						input.skipTag();
					}
				}
				input.readNextEntry();
				break;
			}
			default:
				throw new NBTException("Unexpected type: " + input.getType());
			}
			return value;
		}

		@Override
		public List<TagType> getTags() {
			return CodecTags.COMPOUND_STRING;
		}
		
	}
	
	private static class FilterableStreamCodec<T> implements StreamCodec<Filterable<T>> {

		private final StreamCodec<T> codec;
		
		private FilterableStreamCodec(StreamCodec<T> codec) {
			this.codec = Objects.requireNonNull(codec);
		}
		
		@Override
		public Class<?> getType() {
			return Filterable.class;
		}

		@Override
		public boolean serialize(Filterable<T> value, ByteBuf output, CodecContext context) throws IOException {
			codec.serialize(value.getRaw(), output, context);
			if (value.hasFiltered()) {
				output.writeBoolean(true);
				codec.serialize(value.getFiltered(), output);
			} else {
				output.writeBoolean(false);
			}
			return true;
		}

		@Override
		public Filterable<T> deserialize(Filterable<T> value, ByteBuf input, CodecContext context) throws IOException {
			Filterable<T> filterable = new Filterable<>();
			filterable.setRaw(codec.deserialize(input, context));
			if (input.readBoolean())
				filterable.setFiltered(codec.deserialize(input, context));
			return filterable;
		}
		
	}

}
