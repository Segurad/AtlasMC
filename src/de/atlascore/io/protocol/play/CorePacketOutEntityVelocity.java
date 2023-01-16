package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityVelocity;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityVelocity extends PacketIO<PacketOutEntityVelocity> {

	@Override
	public void read(PacketOutEntityVelocity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setVelocityX(in.readShort()/8000);
		packet.setVelocityY(in.readShort()/8000);
		packet.setVelocityZ(in.readShort()/8000);
	}

	@Override
	public void write(PacketOutEntityVelocity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeShort((int) (packet.getVelocityX()*8000));
		out.writeShort((int) (packet.getVelocityY()*8000));
		out.writeShort((int) (packet.getVelocityZ()*8000));
	}
	
	@Override
	public PacketOutEntityVelocity createPacketData() {
		return new PacketOutEntityVelocity();
	}

}
