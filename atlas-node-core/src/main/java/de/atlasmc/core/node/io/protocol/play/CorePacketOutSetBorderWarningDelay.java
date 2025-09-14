package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSetBorderWarningDelay;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBorderWarningDelay implements PacketIO<PacketOutSetBorderWarningDelay> {

	@Override
	public void read(PacketOutSetBorderWarningDelay packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.delay = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetBorderWarningDelay packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.delay, out);
	}

	@Override
	public PacketOutSetBorderWarningDelay createPacketData() {
		return new PacketOutSetBorderWarningDelay();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetBorderWarningDelay.class);
	}

}
