package de.atlasmc.io.metadata;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public final class VarIntMetaType extends MetaDataType<Integer> {

	public VarIntMetaType(int type) {
		super(type, Number.class);
	}
	
	public VarIntMetaType(int type, boolean optional) {
		super(type, Number.class, optional);
	}

	@Override
	public Integer read(ByteBuf in, CodecContext context) {
		return readVarInt(in);
	}

	@Override
	public void write(Integer data, ByteBuf out, CodecContext context) {
		writeVarInt(data != null ? data : 0, out);
	}

}
