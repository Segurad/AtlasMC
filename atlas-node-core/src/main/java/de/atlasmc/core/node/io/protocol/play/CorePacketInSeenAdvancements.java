package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInSeenAdvancements;
import de.atlasmc.node.io.protocol.play.PacketInSeenAdvancements.Action;
import io.netty.buffer.ByteBuf;

public class CorePacketInSeenAdvancements implements PacketIO<PacketInSeenAdvancements> {

	@Override
	public void read(PacketInSeenAdvancements packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.action = Action.getByID(in.readInt());
		packet.tabID = readIdentifier(in);
	}

	@Override
	public void write(PacketInSeenAdvancements packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeInt(packet.action.getID());
		writeIdentifier(packet.tabID, out);
	}
	
	@Override
	public PacketInSeenAdvancements createPacketData() {
		return new PacketInSeenAdvancements();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSeenAdvancements.class);
	}
	
}
