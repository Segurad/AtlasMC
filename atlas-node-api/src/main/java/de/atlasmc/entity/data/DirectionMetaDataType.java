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
        switch (face) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.EAST;
            default:
                throw new IllegalStateException("Invalid face id: " + face);
        }
    }

    @Override
    public void write(BlockFace data, ByteBuf out) {
        int i = 0;
        switch (data) {
            case DOWN:
                i = 0;
                break;
            case UP:
                i = 1;
                break;
            case NORTH:
                i = 2;
                break;
            case SOUTH:
                i = 3;
                break;
            case WEST:
                i = 4;
                break;
            case EAST:
                i = 5;
                break;
            default:
                throw new IllegalArgumentException("Invalid BlockFace: " + data.name());
        }
        writeVarInt(i, out);
    }

}
