package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutGameEvent;
import de.atlasmc.node.io.protocol.play.PacketOutGameEvent.GameEventType;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutGameEvent implements PacketIO<PacketOutGameEvent> {

	@Override
	public void read(PacketOutGameEvent packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.event = EnumUtil.getByID(GameEventType.class, in.readUnsignedByte());
		packet.value = in.readFloat();
	}

	@Override
	public void write(PacketOutGameEvent packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.event.getID());
		out.writeFloat(packet.value);
	}
	
	@Override
	public PacketOutGameEvent createPacketData() {
		return new PacketOutGameEvent();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutGameEvent.class);
	}
	
}
