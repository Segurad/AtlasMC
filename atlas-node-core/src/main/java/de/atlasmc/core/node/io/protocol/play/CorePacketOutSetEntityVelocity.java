package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSetEntityVelocity;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetEntityVelocity implements PacketIO<PacketOutSetEntityVelocity> {

	@Override
	public void read(PacketOutSetEntityVelocity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.x = ((double)in.readShort())/8000d;
		packet.y = ((double)in.readShort())/8000d;
		packet.z = ((double)in.readShort())/8000d;
	}

	@Override
	public void write(PacketOutSetEntityVelocity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeShort((int) (packet.x*8000));
		out.writeShort((int) (packet.y*8000));
		out.writeShort((int) (packet.z*8000));
	}
	
	@Override
	public PacketOutSetEntityVelocity createPacketData() {
		return new PacketOutSetEntityVelocity();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetEntityVelocity.class);
	}

}
