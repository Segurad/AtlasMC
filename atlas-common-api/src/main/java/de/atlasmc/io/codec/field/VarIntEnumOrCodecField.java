package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class VarIntEnumOrCodecField<T, V, E extends Enum<E> & IDHolder> extends AbstractObjectField<T, V> {
	
	private final Class<E> clazz;
	private final StreamCodec<V> codec;
	
	@SuppressWarnings("unchecked")
	public VarIntEnumOrCodecField(Function<T, V> get, BiConsumer<T, V> set, Class<E> clazz, StreamCodec<? extends V> codec) {
		super(get, set);
		this.clazz = Objects.requireNonNull(clazz);
		this.codec = (StreamCodec<V>) Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V value = get.apply(type);
		if (clazz.isInstance(value)) {
			@SuppressWarnings("unchecked")
			E eValue = (E) value;
			PacketUtil.writeVarInt(eValue.getID() + 1, buf);
			return true;
		} else {
			PacketUtil.writeVarInt(0, buf);
			return codec.serialize(value, buf, context);
		}
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		int id = PacketUtil.readVarInt(buf);
		if (id == 0) {
			set.accept(type, codec.deserialize(null, buf, context));
		} else {
			E raw = EnumUtil.getByID(clazz, id - 1);
			@SuppressWarnings("unchecked")
			V value = (V) raw;
			set.accept(type, value);
		}
	}

}
