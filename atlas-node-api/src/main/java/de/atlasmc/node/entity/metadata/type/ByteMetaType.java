package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

@Singleton
public class ByteMetaType extends MetaDataType<Byte> {
	
	public ByteMetaType(int type) {
		super(type, Number.class);
	}
	
	@Override
    public Byte read(ByteBuf in, CodecContext context) {
        return in.readByte();
    }

    @Override
    public void write(Byte data, ByteBuf out, CodecContext context) {
        out.writeByte(data & 0xFF);
    }

}
