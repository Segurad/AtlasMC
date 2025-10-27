package de.atlasmc.node.entity.metadata.type;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

final class OptVarIntMetaType extends MetaDataType<Integer> {
	
	public OptVarIntMetaType(int type) {
		super(type, Number.class, true);
	}
	
    @Override
    public Integer read(ByteBuf in, CodecContext context) {
        int i = readVarInt(in);
        return i == 0 ? null : i - 1;
    }

    @Override
    public void write(Integer data, ByteBuf out, CodecContext context) {
        if (data == null) {
        	writeVarInt(0, out);
    	} else {
        	writeVarInt(data + 1, out);
        }
    }

}
