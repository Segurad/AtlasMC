package de.atlasmc.entity.data;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import de.atlasmc.block.BlockFace;
import io.netty.buffer.ByteBuf;

public class DirectionMetaDataType extends MetaDataType<BlockFace> {

	public DirectionMetaDataType(int type) {
		super(type, BlockFace.class);
	}
	
	@Override
    public BlockFace read(ByteBuf in) {
        int face = readVarInt(in);
        return BlockFace.getByFaceID(face);
    }

    @Override
    public void write(BlockFace data, ByteBuf out) {
        int i = data.getFaceID();
        writeVarInt(i, out);
    }

}
