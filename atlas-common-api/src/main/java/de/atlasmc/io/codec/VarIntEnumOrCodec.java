package de.atlasmc.io.codec;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CodecException;

final class VarIntEnumOrCodec<T, E extends Enum<E> & IDHolder> implements StreamCodec<T> {

	private final Class<?> typeClass;
	private final Class<E> clazz;
	private final StreamCodec<T> codec;
	
	@SuppressWarnings("unchecked")
	public VarIntEnumOrCodec(Class<T> typeClass, Class<E> clazz, StreamCodec<? extends T> codec) {
		this.typeClass = typeClass;
		if (!typeClass.isAssignableFrom(clazz))
			throw new IllegalArgumentException("Enum class: " + clazz + " must be assignable from: " + typeClass);
		this.clazz = clazz;
		this.codec = (StreamCodec<T>) Objects.requireNonNull(codec);
	}
	
	@Override
	public Class<?> getType() {
		return typeClass;
	}

	@Override
	public boolean serialize(T value, ByteBuf output, CodecContext context) throws IOException {
		if (clazz.isInstance(value) && value instanceof IDHolder v) {
			int id = v.getID();
			PacketUtil.writeVarInt(id + 1, output);
		}
		if (!codec.getType().isInstance(value))
			throw new CodecException("Codec: " + codec.getType() + " is incompatible with type: " + value.getClass());
		PacketUtil.writeVarInt(0, output);
		codec.serialize(value, output, context);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(T value, ByteBuf input, CodecContext context) throws IOException {
		int id = PacketUtil.readVarInt(input);
		if (id > 0)
			return (T) EnumUtil.getByID(clazz, id - 1);
		else
			return codec.deserialize(input, context);
	}

}
