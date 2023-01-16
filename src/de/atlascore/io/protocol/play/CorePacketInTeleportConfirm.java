package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;

import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.protocol.play.PacketInTeleportConfirm;
import io.netty.buffer.ByteBuf;

public class CorePacketInTeleportConfirm extends PacketIO<PacketInTeleportConfirm> {

	@Override
	public void read(PacketInTeleportConfirm packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTeleportID(readVarInt(in));
	}

	@Override
	public void write(PacketInTeleportConfirm packet, ByteBuf out, ConnectionHandler handler) {
		writeVarInt(packet.getTeleportID(), out);
	}

	@Override
	public PacketInTeleportConfirm createPacketData() {
		return new PacketInTeleportConfirm();
	}

}
