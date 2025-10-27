package de.atlasmc.node.entity.metadata.type;

import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.writeString;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

@Singleton
public class StringMetaType extends MetaDataType<String> {
	
	public StringMetaType(int type) {
		super(type, String.class, false);
	}
	
    @Override
    public String read(ByteBuf in, CodecContext context) {
        return readString(in);
    }

    @Override
    public void write(String data, ByteBuf out, CodecContext context) {
        writeString(data, out);
    }

}
