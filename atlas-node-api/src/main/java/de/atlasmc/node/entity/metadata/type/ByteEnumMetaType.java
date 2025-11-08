package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.IDHolder;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class ByteEnumMetaType<T extends Enum<T> & IDHolder> extends MetaDataType<T> {
	
	private final Class<T> enumClass;
	
	public ByteEnumMetaType(int id, Class<T> enumClass) {
		super(id, enumClass);
		this.enumClass = enumClass;
	}
	
	@Override
    public T read(ByteBuf in, CodecContext context) {
        return EnumUtil.getByID(enumClass, in.readByte());
    }

    @Override
    public void write(T data, ByteBuf out, CodecContext context) {
        out.writeByte(data.getID() & 0xFF);
    }

}
