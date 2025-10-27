package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

@Singleton
public class OptLongMetaType extends MetaDataType<Long> {
	
	public OptLongMetaType(int type) {
		super(type, Number.class, true);
	}
	
    @Override
    public Long read(ByteBuf in, CodecContext context) {
        if (in.readBoolean()) 
        	return in.readLong();
        return null;
    }

    @Override
    public void write(Long data, ByteBuf out, CodecContext context) {
        out.writeBoolean(data != null);
        if (data == null) 
        	return;
        long l = data;
        out.writeLong(l);
    }

}
