package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInDebugSampleSubscription;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInDebugSampleSubscription implements PacketIO<PacketInDebugSampleSubscription> {

	@Override
	public void read(PacketInDebugSampleSubscription packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.type = readVarInt(in);
	}

	@Override
	public void write(PacketInDebugSampleSubscription packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
