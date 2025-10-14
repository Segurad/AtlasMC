package de.atlasmc.node.entity.data;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class VarIntEnumMetaDataType<T extends Enum<T> & IDHolder> extends MetaDataType<T> {

	private final Class<T> enumClass;
	
	public VarIntEnumMetaDataType(int type, Class<T> typeClass) {
		super(type, typeClass);
		this.enumClass = typeClass;
	}

	@Override
	public T read(ByteBuf in) {
		int id = PacketUtil.readVarInt(in);
		return EnumUtil.getByID(enumClass, id);
	}

	@Override
	public void write(T data, ByteBuf out) {
		PacketUtil.writeVarInt(data.getID(), out);
	}

}
