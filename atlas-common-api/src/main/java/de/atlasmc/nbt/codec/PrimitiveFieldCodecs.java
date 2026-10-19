package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;

public class PrimitiveFieldCodecs {
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> buildByte(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, TagType.BYTE);
	}
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> buildShort(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, TagType.SHORT);
	}
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> buildInt(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, TagType.INT);
	}
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToLongFunction<T>, ObjLongConsumer<T>> buildLong(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, TagType.LONG);
	}
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToFloatFunction<T>, ObjFloatConsumer<T>> buildFloat(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, TagType.FLOAT);
	}
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>> buildDouble(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, TagType.DOUBLE);
	}
	
	public static <T> PrimitiveFieldCodecBuilder<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>> buildBoolean(Class<T> clazz) {
		return new PrimitiveFieldCodecBuilder<>(clazz, null);
	}
	
	private static final class ByteFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToIntFunction<T>, ObjIntConsumer<T>> {

		private final byte defaultValue;
		
		public ByteFieldCodec(PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toByte(builder.defaultValue());
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			byte v = (byte) getter.applyAsInt(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeByteTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readByteTag());
			return type;
		}
		
	}
	
	private static final class ShortFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToIntFunction<T>, ObjIntConsumer<T>> {

		private final short defaultValue;
		
		public ShortFieldCodec(PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toShort(builder.defaultValue());
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			short v = (short) getter.applyAsInt(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeShortTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readShortTag());
			return type;
		}
		
	}
	
	private static final class IntFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToIntFunction<T>, ObjIntConsumer<T>> {

		private final int defaultValue;
		
		public IntFieldCodec(PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toInt(builder.defaultValue());
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			int v = getter.applyAsInt(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeIntTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readIntTag());
			return type;
		}
		
	}
	
	private static final class LongFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToLongFunction<T>, ObjLongConsumer<T>> {

		private final long defaultValue;
		
		public LongFieldCodec(PrimitiveFieldCodecBuilder<T, ToLongFunction<T>, ObjLongConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toLong(builder.defaultValue());
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			long v = getter.applyAsLong(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeLongTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readLongTag());
			return type;
		}
		
	}
	
	private static final class FloatFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToFloatFunction<T>, ObjFloatConsumer<T>> {

		private final float defaultValue;
		
		public FloatFieldCodec(PrimitiveFieldCodecBuilder<T, ToFloatFunction<T>, ObjFloatConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toFloat(builder.defaultValue());
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			float v = getter.applyAsFloat(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeFloatTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readFloatTag());
			return type;
		}
		
	}
	
	private static final class DoubleFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>> {

		private final double defaultValue;
		
		public DoubleFieldCodec(PrimitiveFieldCodecBuilder<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toDouble(builder.defaultValue());
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			double v = getter.applyAsDouble(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeDoubleTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readDoubleTag());
			return type;
		}
		
	}
	
	private static final class BooleanFieldCodec<T> extends AbstractPrimitiveFieldCodec<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>> {

		private final boolean defaultValue;
		
		public BooleanFieldCodec(PrimitiveFieldCodecBuilder<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>> builder) {
			super(builder);
			this.defaultValue = NumberConversion.toInt(builder.defaultValue()) == 1;
		}

		@Override
		public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
			if (type == null)
				return false;
			boolean v = getter.applyAsBoolean(type);
			if (useDefault && v == defaultValue)
				return true;
			writer.writeByteTag(key, v);
			return true;
		}

		@Override
		public T deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
			setter.accept(type, reader.readBoolean());
			return type;
		}
		
	}
	
	private static abstract class AbstractPrimitiveFieldCodec<T, G, S> extends AbstractCodec<T> {

		protected final List<TagType> tags;
		protected final G getter;
		protected final S setter;
		protected final boolean useDefault;
		
		public AbstractPrimitiveFieldCodec(PrimitiveFieldCodecBuilder<T, G, S> builder) {
			super(builder);
			tags = Objects.requireNonNull(builder.getTags(), "tags");
			getter = Objects.requireNonNull(builder.getter(), "getter");
			setter = Objects.requireNonNull(builder.setter(), "setter");
			useDefault = builder.defaultValue() != null;
		}

		@Override
		public List<TagType> getTags() {
			return tags;
		}
		
	}
	
	public static final class PrimitiveFieldCodecBuilder<T, G, S> extends AbstractCodecBuilder<T, PrimitiveFieldCodecBuilder<T, G, S>> {

		private G getter;
		private S setter;
		private Number defaultValue;
		private TagType tagType;
		
		private PrimitiveFieldCodecBuilder(Class<T> type, TagType tagType) {
			super(type);
			this.tagType = tagType;
		}
		
		public PrimitiveFieldCodecBuilder<T, G, S> defaultValue(Number defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}
		
		public PrimitiveFieldCodecBuilder<T, G, S> defaultValue(boolean defaultValue) {
			this.defaultValue = defaultValue ? 1 : 0;
			return this;
		}
		
		public Number defaultValue() {
			return defaultValue;
		}
		
		public G getter() {
			return getter;
		}
		
		public PrimitiveFieldCodecBuilder<T, G, S> getter(G getter) {
			this.getter = getter;
			return this;
		}
		
		public S setter() {
			return setter;
		}
		
		public PrimitiveFieldCodecBuilder<T, G, S> setter(S setter) {
			this.setter = setter;
			return this;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public NBTCodec<T> build() {
			return switch (tagType) {
			case BYTE -> new ByteFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>>) this);
			case SHORT -> new ShortFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>>) this);
			case INT -> new IntFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>>) this);
			case LONG -> new LongFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToLongFunction<T>, ObjLongConsumer<T>>) this);
			case FLOAT -> new FloatFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToFloatFunction<T>, ObjFloatConsumer<T>>) this);
			case DOUBLE -> new DoubleFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>>) this);
			case null -> new BooleanFieldCodec<T>((PrimitiveFieldCodecBuilder<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>>) this);
			default ->
				throw new IllegalArgumentException("Unsupported type: " + tagType);
			};
		}

		public List<TagType> getTags() {
			return switch (tagType) {
			case BYTE -> CodecTags.BYTE;
			case SHORT -> CodecTags.SHORT;
			case INT -> CodecTags.INT;
			case LONG -> CodecTags.LONG;
			case FLOAT -> CodecTags.FLOAT;
			case DOUBLE -> CodecTags.DOUBLE;
			case null -> CodecTags.BYTE;
			default ->
				throw new IllegalArgumentException("Unsupported type: " + tagType);
			};
		}

		@Override
		protected PrimitiveFieldCodecBuilder<T, G, S> getThis() {
			return this;
		}
		
	}

}
