package de.atlasmc.io.codec.constructor;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public abstract class AbstractVarIntConstructor<T, K extends IDHolder> implements Constructor<T> {

	protected final Function<K, T> constructor;
	protected final Function<T, K> keyReverseSupplier;

	public AbstractVarIntConstructor(Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = Objects.requireNonNull(constructor);
		this.keyReverseSupplier = Objects.requireNonNull(keyReverseSupplier);
	}
	
	protected abstract K getKey(int id);
	
	@Override
	public T construct(ByteBuf in, CodecContext context) throws IOException {
		int id = PacketUtil.readVarInt(in);
		K key = getKey(id);
		return constructor.apply(key);
	}

	@Override
	public void serialize(T value, ByteBuf out, CodecContext context) throws IOException {
		if (keyReverseSupplier == null)
			return;
		K key = keyReverseSupplier.apply(value);
		if (key == null)
			return;
		PacketUtil.writeVarInt(key.getID(), out);
	}

}
