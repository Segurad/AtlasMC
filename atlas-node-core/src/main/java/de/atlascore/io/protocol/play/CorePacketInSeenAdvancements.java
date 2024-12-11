package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSeenAdvancements;
import de.atlasmc.io.protocol.play.PacketInSeenAdvancements.Action;
import io.netty.buffer.ByteBuf;

public class CorePacketInSeenAdvancements implements PacketIO<PacketInSeenAdvancements> {

	@Override
	public void read(PacketInSeenAdvancements packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setAction(Action.getByID(in.readInt()));
		packet.setTabID(readString(in));
	}

	@Override
	public void write(PacketInSeenAdvancements packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeInt(packet.getAction().getID());
		writeString(packet.getTabID(), out);
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
