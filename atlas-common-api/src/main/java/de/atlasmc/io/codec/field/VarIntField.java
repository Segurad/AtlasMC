package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class VarIntField<T> extends StreamField<T> {

	private final ToIntFunction<T> get;
	private final ObjIntConsumer<T> set;
	
	public VarIntField(ToIntFunction<T> get, ObjIntConsumer<T> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		PacketUtil.writeVarInt(get.applyAsInt(type), buf);
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, PacketUtil.readVarInt(buf));
	}

}
