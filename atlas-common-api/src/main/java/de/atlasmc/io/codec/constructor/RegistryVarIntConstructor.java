package de.atlasmc.io.codec.constructor;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class RegistryVarIntConstructor<T, K extends ProtocolRegistryValue> extends AbstractRegistryConstructor<T, K> {

	private final Function<T, K> keyReverseSupplier;
	
	public RegistryVarIntConstructor(RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		super(registry, constructor);
		this.keyReverseSupplier = keyReverseSupplier;
	}

	@Override
	public T construct(ByteBuf in, CodecContext context) throws IOException {
		int id = PacketUtil.readVarInt(in);
		K regValue = registry.getValue(id);
		return constructor.apply(regValue);
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
