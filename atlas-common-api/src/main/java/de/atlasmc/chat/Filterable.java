package de.atlasmc.chat;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
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
		return null;
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
	
	private static class FilterableStreamCodec<T> implements StreamCodec<Filterable<T>> {

		private final StreamCodec<T> codec;
		
		private FilterableStreamCodec(StreamCodec<T> codec) {
			this.codec = Objects.requireNonNull(codec);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Class<? extends Filterable<T>> getType() {
			return (Class<? extends Filterable<T>>) Filterable.class;
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
