package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

@Singleton
public class BooleanMetaType extends MetaDataType<Boolean> {
	
	public BooleanMetaType(int type) {
		super(type, Boolean.class);
	}
	
    @Override
    public Boolean read(ByteBuf in, CodecContext context) {
        return in.readBoolean();
    }

    @Override
    public void write(Boolean data, ByteBuf out, CodecContext context) {
        out.writeBoolean(data);
    }

}
