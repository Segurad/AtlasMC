package de.atlasmc.node.entity.data;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import io.netty.buffer.ByteBuf;

final class VarIntMetaDataType extends MetaDataType<Integer> {

	public VarIntMetaDataType(int type) {
		super(type, Number.class);
	}

	@Override
	public Integer read(ByteBuf in) {
		return readVarInt(in);
	}

	@Override
	public void write(Integer data, ByteBuf out) {
		writeVarInt(data, out);
	}

}
