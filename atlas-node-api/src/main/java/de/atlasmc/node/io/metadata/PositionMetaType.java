package de.atlasmc.node.io.metadata;

import java.io.IOException;

import org.joml.Vector3i;

import de.atlasmc.io.metadata.MetaDataType;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class PositionMetaType extends MetaDataType<Vector3i> {

	public PositionMetaType(int type) {
		super(type, Vector3i.class);
	}

	@Override
	public Vector3i read(ByteBuf in, CodecContext context) throws IOException {
		return MathUtil.getPositionVector(new Vector3i(), in.readLong());
	}

	@Override
	public void write(Vector3i data, ByteBuf out, CodecContext context) throws IOException {
		out.writeLong(MathUtil.toPosition(data));
	}

}
