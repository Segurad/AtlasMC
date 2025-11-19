package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSeenAdvancements;
import de.atlasmc.node.io.protocol.play.PacketInSeenAdvancements.Action;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketInSeenAdvancements implements PacketIO<PacketInSeenAdvancements> {

	@Override
	public void read(PacketInSeenAdvancements packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.action = EnumUtil.getByID(Action.class, in.readInt());
		packet.tabID = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
	}

	@Override
	public void write(PacketInSeenAdvancements packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeInt(packet.action.getID());
		NamespacedKey.STREAM_CODEC.serialize(packet.tabID, out, null);
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
