package de.atlasmc.entity.data;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import io.netty.buffer.ByteBuf;

final class OptVarIntMetaDataType extends MetaDataType<Integer> {
	
	public OptVarIntMetaDataType(int type) {
		super(type, Number.class, true);
	}
	
    @Override
    public Integer read(ByteBuf in) {
        int i = readVarInt(in);
        return i == 0 ? null : i - 1;
    }

    @Override
    public void write(Integer data, ByteBuf out) {
        if (data == null) {
        	writeVarInt(0, out);
    	} else {
        	writeVarInt(data + 1, out);
        }
    }

}
