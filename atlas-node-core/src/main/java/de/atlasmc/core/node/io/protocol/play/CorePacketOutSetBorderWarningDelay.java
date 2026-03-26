package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetBorderWarningDelay;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBorderWarningDelay implements PacketCodec<PacketOutSetBorderWarningDelay> {

	@Override
	public void deserialize(PacketOutSetBorderWarningDelay packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.delay = readVarInt(in);
	}

	@Override
	public void serialize(PacketOutSetBorderWarningDelay packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
