package de.atlasmc.node.entity.metadata.type;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

@Singleton
public class FloatMetaType extends MetaDataType<Float> {
	
	public FloatMetaType(int type) {
		super(type, Number.class, false);
	}
	
	@Override
    public Float read(ByteBuf in, CodecContext context) {
        return in.readFloat();
    }

    @Override
    public void write(Float data, ByteBuf out, CodecContext context) {
        out.writeFloat(data);
    }

}
