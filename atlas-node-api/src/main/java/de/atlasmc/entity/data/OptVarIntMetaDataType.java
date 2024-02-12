package de.atlasmc.entity.data;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;

final class OptVarIntMetaDataType extends MetaDataType<Integer> {
	
	public OptVarIntMetaDataType(int type) {
		super(type, Number.class, true);
	}
	
    @Override
    public Integer read(ByteBuf in) {
        int i = AbstractPacket.readVarInt(in);
        return i == 0 ? null : i - 1;
    }

    @Override
    public void write(Integer data, ByteBuf out) {
        if (data == null) 
        	AbstractPacket.writeVarInt(0, out);
        AbstractPacket.writeVarInt(data + 1, out);
    }

}
