package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInConfirmTeleport;
import io.netty.buffer.ByteBuf;

public class CorePacketInConfirmTeleport implements PacketCodec<PacketInConfirmTeleport> {

	@Override
	public void deserialize(PacketInConfirmTeleport packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.teleportID = readVarInt(in);
	}

	@Override
	public void serialize(PacketInConfirmTeleport packet, ByteBuf out, ConnectionHandler handler) {
		writeVarInt(packet.teleportID, out);
	}

	@Override
	public PacketInConfirmTeleport createPacketData() {
		return new PacketInConfirmTeleport();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInConfirmTeleport.class);
	}

}
