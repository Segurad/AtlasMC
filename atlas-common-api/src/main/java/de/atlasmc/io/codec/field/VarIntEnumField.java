package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class VarIntEnumField<T, V extends Enum<V> & IDHolder> extends AbstractObjectField<T, V> {

	private Class<V> clazz;
	
	public VarIntEnumField(Function<T, V> get, BiConsumer<T, V> set, Class<V> clazz) {
		super(get, set);
		this.clazz = Objects.requireNonNull(clazz);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V v = get.apply(type);
		if (v == null)
			throw new IllegalStateException("Value can not be null!");
		PacketUtil.writeVarInt(v.getID(), buf);
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, EnumUtil.getByID(clazz, PacketUtil.readVarInt(buf)));
	}
	
	

}
