package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEndCombat;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutEndCombat implements PacketIO<PacketOutEndCombat> {

	@Override
	public void read(PacketOutEndCombat packet, ByteBuf in, ConnectionHandler con) throws IOException {
		writeVarInt(packet.duration, in);
	}

	@Override
	public void write(PacketOutEndCombat packet, ByteBuf out, ConnectionHandler con) throws IOException {
		packet.duration = readVarInt(out);
	}

	@Override
	public PacketOutEndCombat createPacketData() {
		return new PacketOutEndCombat();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEndCombat.class);
	}

}
