package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetPlayerOnGround;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetPlayerOnGround implements PacketIO<PacketInSetPlayerOnGround> {

	@Override
	public void read(PacketInSetPlayerOnGround packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketInSetPlayerOnGround packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeBoolean(packet.isOnGround());
	}
	
	@Override
	public PacketInSetPlayerOnGround createPacketData() {
		return new PacketInSetPlayerOnGround();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetPlayerOnGround.class);
	}

}
