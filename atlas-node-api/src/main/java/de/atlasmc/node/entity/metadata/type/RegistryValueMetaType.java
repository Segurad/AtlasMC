package de.atlasmc.node.entity.metadata.type;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class RegistryValueMetaType<T extends ProtocolRegistryValue> extends MetaDataType<T> {

	private final RegistryKey<T> registry;
	
	public RegistryValueMetaType(int type, Class<T> clazz, RegistryKey<T> registry) {
		super(type, clazz);
		this.registry = Objects.requireNonNull(registry);
	}

	@Override
	public T read(ByteBuf in, CodecContext context) throws IOException {
		ProtocolRegistry<T> registry = (ProtocolRegistry<T>) this.registry.get();
		return registry.getByID(PacketUtil.readVarInt(in));
	}

	@Override
	public void write(T data, ByteBuf out, CodecContext context) throws IOException {
		PacketUtil.writeVarInt(data.getID(), out);
	}

}
