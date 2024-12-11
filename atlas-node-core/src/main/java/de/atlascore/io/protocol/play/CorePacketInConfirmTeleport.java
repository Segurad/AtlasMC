package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;

import static de.atlasmc.io.protocol.ProtocolUtil.*;
import de.atlasmc.io.protocol.play.PacketInConfirmTeleport;
import io.netty.buffer.ByteBuf;

public class CorePacketInConfirmTeleport implements PacketIO<PacketInConfirmTeleport> {

	@Override
	public void read(PacketInConfirmTeleport packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTeleportID(readVarInt(in));
	}

	@Override
	public void write(PacketInConfirmTeleport packet, ByteBuf out, ConnectionHandler handler) {
		writeVarInt(packet.getTeleportID(), out);
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
