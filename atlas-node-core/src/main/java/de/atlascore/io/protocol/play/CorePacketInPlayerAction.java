package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.List;

import de.atlasmc.block.BlockFace;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAction implements PacketIO<PacketInPlayerAction> {

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
	public void read(PacketInPlayerAction packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setStatus(readVarInt(in));
		packet.setPosition(in.readLong());
		packet.setFace(FACES.get(in.readByte()));
		packet.setSequence(readVarInt(in));
	}

	@Override
	public void write(PacketInPlayerAction packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getStatus(), out);
		out.writeLong(packet.getPosition());
		out.writeByte(FACES.indexOf(packet.getFace()));
		writeVarInt(packet.getSequence(), out);
	}

	@Override
	public PacketInPlayerAction createPacketData() {
		return new PacketInPlayerAction();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerAction.class);
	}

}
