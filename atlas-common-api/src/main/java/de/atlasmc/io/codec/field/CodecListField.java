package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CodecException;

public class CodecListField<T, V> extends AbstractCollectionField<T, List<V>> {

	private final StreamCodec<V> codec;
	private final int maxValues;
	
	public CodecListField(ToBooleanFunction<T> has, Function<T, List<V>> get, StreamCodec<V> codec) {
		this(has, get, codec, Integer.MAX_VALUE);
	}
	
	public CodecListField(ToBooleanFunction<T> has, Function<T, List<V>> get, StreamCodec<V> codec, int maxValues) {
		super(has, get);
		this.codec = Objects.requireNonNull(codec);
		this.maxValues = maxValues;
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		if (!has.applyAsBoolean(type)) {
			PacketUtil.writeVarInt(0, buf);
			return true;
		}
		var list = get.apply(type);
		final int size = list.size();
		if (size == 0) {
			PacketUtil.writeVarInt(size, buf);
			return true;
		}
		if (size > maxValues)
			throw new CodecException("Invalid list size:" + size + " expected: " + maxValues);
		PacketUtil.writeVarInt(size, buf);
		for (int i = 0; i < size; i++) {
			codec.serialize(list.get(i), buf);
		}
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		final int size = PacketUtil.readVarInt(buf);
		if (size == 0)
			return;
		if (size > maxValues)
			throw new CodecException("Invalid list size:" + size + " expected: " + maxValues);
		var list = get.apply(type);
		for (int i = 0; i < size; i++) {
			list.add(codec.deserialize(buf, context));
		}
	}

}
