package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.List;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.block.BlockFace;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerDigging;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerDigging extends PacketIO<PacketInPlayerDigging> {

	/**
	 * List of all faces indexed by the id needed for the packet
	 */
	public static final List<BlockFace> FACES = List.of(
		BlockFace.DOWN,
		BlockFace.UP,
		BlockFace.NORTH,
		BlockFace.SOUTH,
		BlockFace.WEST,
		BlockFace.EAST
	);
	
	@Override
	public void read(PacketInPlayerDigging packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setStatus(readVarInt(in));
		packet.setPosition(in.readLong());
		packet.setFace(FACES.get(in.readByte()));
	}

	@Override
	public void write(PacketInPlayerDigging packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getStatus(), out);
		out.writeLong(packet.getPosition());
		out.writeByte(FACES.indexOf(packet.getFace()));
	}

	@Override
	public PacketInPlayerDigging createPacketData() {
		return new PacketInPlayerDigging();
	}

}
