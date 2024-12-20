package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutGameEvent;
import de.atlasmc.io.protocol.play.PacketOutGameEvent.GameEventType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutGameEvent implements PacketIO<PacketOutGameEvent> {

	@Override
	public void read(PacketOutGameEvent packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.event = GameEventType.getByID(in.readUnsignedByte());
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
