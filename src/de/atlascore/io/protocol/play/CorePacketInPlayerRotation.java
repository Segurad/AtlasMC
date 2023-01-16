package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerRotation extends PacketIO<PacketInPlayerRotation> {
	
	@Override
	public void read(PacketInPlayerRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketInPlayerRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketInPlayerRotation createPacketData() {
		return new PacketInPlayerRotation();
	}	

}
