package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.block.BlockFace;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerDigging;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerDigging extends AbstractPacket implements PacketInPlayerDigging {

	public static final BlockFace[] faces = new BlockFace[] {
		BlockFace.DOWN,
		BlockFace.UP,
		BlockFace.NORTH,
		BlockFace.SOUTH,
		BlockFace.WEST,
		BlockFace.EAST
	};
	
	public CorePacketInPlayerDigging() {
		super(CoreProtocolAdapter.VERSION);
	}

	private int status;
	private long pos;
	private byte face;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		status = readVarInt(in);
		pos = in.readLong();
		face = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(status, out);
		out.writeLong(pos);
		out.writeByte(face);
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public BlockFace getFace() {
		if (face < 0 || face > 5)
			return BlockFace.UP;
		return faces[face];
	}
	
	

}
