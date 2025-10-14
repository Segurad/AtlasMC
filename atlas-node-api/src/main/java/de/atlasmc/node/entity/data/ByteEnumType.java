package de.atlasmc.node.entity.data;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class ByteEnumType<T extends Enum<T> & IDHolder> extends MetaDataType<T> {
	
	private final Class<T> enumClass;
	
	public ByteEnumType(int id, Class<T> enumClass) {
		super(id, enumClass);
		this.enumClass = enumClass;
	}
	
	@Override
    public T read(ByteBuf in) {
        return EnumUtil.getByID(enumClass, in.readByte());
    }

    @Override
    public void write(T data, ByteBuf out) {
        out.writeByte(data.getID() & 0xFF);
    }

}
