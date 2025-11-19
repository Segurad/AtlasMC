package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTNIOReader;
import de.atlasmc.nbt.io.NBTNIOWriter;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class NBTCodecField<T, V> extends AbstractObjectField<T, V> {

	private final NBTCodec<V> codec;
	
	public NBTCodecField(Function<T, V> get, BiConsumer<T, V> set, NBTCodec<V> codec) {
		super(get, set);
		this.codec = Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		try (NBTWriter writer = new NBTNIOWriter(buf, true)) {
			V value = get.apply(type);
			return codec.serialize(null, value, writer, context);
		}
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		try (NBTReader reader = new NBTNIOReader(buf, true)) {
			V value = codec.deserialize(reader, context);
			set.accept(type, value);
		}
	}

}
