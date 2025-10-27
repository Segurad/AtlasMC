package de.atlasmc.node.entity.metadata.type;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class DirectionMetaType extends MetaDataType<BlockFace> {

	public DirectionMetaType(int type) {
		super(type, BlockFace.class);
	}
	
	@Override
    public BlockFace read(ByteBuf in, CodecContext context) {
        int face = readVarInt(in);
        return BlockFace.getByFaceID(face);
    }

    @Override
    public void write(BlockFace data, ByteBuf out, CodecContext context) {
        int i = data.getFaceID();
        writeVarInt(i, out);
    }

}
