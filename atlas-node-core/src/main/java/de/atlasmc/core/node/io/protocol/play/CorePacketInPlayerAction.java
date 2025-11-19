package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.io.protocol.play.PacketInPlayerAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAction implements PacketIO<PacketInPlayerAction> {
	
	@Override
	public void read(PacketInPlayerAction packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.status = readVarInt(in);
		packet.position = in.readLong();
		packet.face = BlockFace.getByFaceID(in.readByte());
		packet.sequence = readVarInt(in);
	}

	@Override
	public void write(PacketInPlayerAction packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.status, out);
		out.writeLong(packet.position);
		out.writeByte(packet.face.getFaceID());
		writeVarInt(packet.sequence, out);
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
