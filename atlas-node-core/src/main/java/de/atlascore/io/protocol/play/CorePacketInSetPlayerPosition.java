package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetPlayerPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetPlayerPosition implements PacketIO<PacketInSetPlayerPosition> {

	@Override
	public void read(PacketInSetPlayerPosition packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.x = in.readDouble();
		packet.feetY = in.readDouble();
		packet.z = in.readDouble();
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketInSetPlayerPosition packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.feetY);
		out.writeDouble(packet.z);
		out.writeByte(packet.flags);
	}
	
	@Override
	public PacketInSetPlayerPosition createPacketData() {
		return new PacketInSetPlayerPosition();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetPlayerPosition.class);
	}

}
