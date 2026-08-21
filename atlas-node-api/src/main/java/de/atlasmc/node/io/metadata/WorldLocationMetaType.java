package de.atlasmc.node.io.metadata;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.metadata.MetaDataType;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class WorldLocationMetaType extends MetaDataType<WorldLocation> {

	public WorldLocationMetaType(int type) {
		super(type, WorldLocation.class, true);
	}

	@SuppressWarnings("unused")
	@Override
	public WorldLocation read(ByteBuf in, CodecContext context) throws IOException {
		if (!in.readBoolean())
			return null;
		NamespacedKey key = NamespacedKey.STREAM_CODEC.deserialize(in);
		long pos = in.readLong();
		// TODO opt pos global to location
		return null;
	}

	@Override
	public void write(WorldLocation data, ByteBuf out, CodecContext context) throws IOException {
		if (data == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			var dim = data.getWorld().getDimension();
			NamespacedKey.STREAM_CODEC.serialize(context.clientSide ? dim.getClientKey() : dim.getNamespacedKey(), out, context);
			out.writeLong(MathUtil.toPosition(data));
		}
	}
	
}
