package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutHurtAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutHurtAnimation implements PacketIO<PacketOutHurtAnimation> {

	@Override
	public void read(PacketOutHurtAnimation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.yaw = in.readFloat();
	}

	@Override
	public void write(PacketOutHurtAnimation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeFloat(packet.yaw);
	}

	@Override
	public PacketOutHurtAnimation createPacketData() {
		return new PacketOutHurtAnimation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutHurtAnimation.class);
	}

}
