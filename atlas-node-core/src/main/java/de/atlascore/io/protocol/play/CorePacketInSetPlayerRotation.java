package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetPlayerRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetPlayerRotation implements PacketIO<PacketInSetPlayerRotation> {
	
	@Override
	public void read(PacketInSetPlayerRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
		packet.onGround = in.readBoolean();
	}

	@Override
	public void write(PacketInSetPlayerRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
		out.writeBoolean(packet.onGround);
	}

	@Override
	public PacketInSetPlayerRotation createPacketData() {
		return new PacketInSetPlayerRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetPlayerRotation.class);
	}	

}
