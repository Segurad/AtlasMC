package de.atlasmc.entity.data;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;

final class VarIntMetaDataType extends MetaDataType<Integer> {

	public VarIntMetaDataType(int type) {
		super(type, Number.class);
	}

	@Override
	public Integer read(ByteBuf in) {
		return AbstractPacket.readVarInt(in);
	}

	@Override
	public void write(Integer data, ByteBuf out) {
		AbstractPacket.writeVarInt(data, out);
	}

}
