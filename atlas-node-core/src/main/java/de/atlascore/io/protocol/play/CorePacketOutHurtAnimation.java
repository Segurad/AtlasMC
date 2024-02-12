package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutHurtAnimation;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

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
