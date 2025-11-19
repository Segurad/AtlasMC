package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class VarIntRegistryOrCodecField<T, V extends ProtocolRegistryValue> extends AbstractObjectField<T, V> {

	private final RegistryKey<V> registry;
	private final StreamCodec<V> codec;
	
	@SuppressWarnings("unchecked")
	public VarIntRegistryOrCodecField(Function<T, V> get, BiConsumer<T, V> set, RegistryKey<V> registry, StreamCodec<? extends V> codec) {
		super(get, set);
		this.registry = Objects.requireNonNull(registry);
		this.codec = (StreamCodec<V>) Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V value = get.apply(type);
		int id = value.getID();
		if (id != -1) {
			PacketUtil.writeVarInt(id + 1, buf);
			return true;
		} else {
			PacketUtil.writeVarInt(0, buf);
			return codec.serialize(value, buf, context);
		}
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		int id = PacketUtil.readVarInt(buf);
		V value;
		if (id == 0) {
			value = codec.deserialize(null, buf, context);
		} else {
			value = registry.getValue(id - 1);
			if (value == null)
				throw new ProtocolException("No registry (" + registry + ") value found with id: " + id);
		}
		set.accept(type, value);
	}

}
