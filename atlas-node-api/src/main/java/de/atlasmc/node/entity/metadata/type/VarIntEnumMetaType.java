package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class VarIntEnumMetaType<T extends Enum<T> & IDHolder> extends MetaDataType<T> {

	private final Class<T> enumClass;
	
	public VarIntEnumMetaType(int type, Class<T> typeClass) {
		super(type, typeClass);
		this.enumClass = typeClass;
	}

	@Override
	public T read(ByteBuf in, CodecContext context) {
		int id = PacketUtil.readVarInt(in);
		return EnumUtil.getByID(enumClass, id);
	}

	@Override
	public void write(T data, ByteBuf out, CodecContext context) {
		PacketUtil.writeVarInt(data.getID(), out);
	}

}
