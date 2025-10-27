package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

@Singleton
public class LongMetaType extends MetaDataType<Long> {
	
	public LongMetaType(int type) {
		super(type, Number.class);
	}
	
    @Override
    public Long read(ByteBuf in, CodecContext context) {
        return in.readLong();
    }

    @Override
    public void write(Long data, ByteBuf out, CodecContext context) {
        out.writeLong(data);
    }

}
