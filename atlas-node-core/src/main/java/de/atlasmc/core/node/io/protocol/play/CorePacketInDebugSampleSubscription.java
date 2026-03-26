package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInDebugSampleSubscription;
import io.netty.buffer.ByteBuf;

public class CorePacketInDebugSampleSubscription implements PacketCodec<PacketInDebugSampleSubscription> {

	@Override
	public void deserialize(PacketInDebugSampleSubscription packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.type = readVarInt(in);
	}

	@Override
	public void serialize(PacketInDebugSampleSubscription packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.type, out);
	}

	@Override
	public PacketInDebugSampleSubscription createPacketData() {
		return new PacketInDebugSampleSubscription();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInDebugSampleSubscription.class);
	}

}
