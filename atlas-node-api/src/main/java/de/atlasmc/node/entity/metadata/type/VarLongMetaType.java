package de.atlasmc.node.entity.metadata.type;

import static de.atlasmc.io.PacketUtil.readVarLong;
import static de.atlasmc.io.PacketUtil.writeVarLong;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class VarLongMetaType extends MetaDataType<Long> {

	public VarLongMetaType(int type) {
		super(type, Number.class);
	}

	@Override
	public Long read(ByteBuf in, CodecContext context) {
		return readVarLong(in);
	}
	
	@Override
	public void write(Long data, ByteBuf out, CodecContext context) {
		writeVarLong(data, out);
	}

}
