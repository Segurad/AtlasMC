package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetHeadRotation;
import de.atlasmc.node.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetHeadRotation implements PacketIO<PacketOutSetHeadRotation> {

	@Override
	public void read(PacketOutSetHeadRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.yaw = MathUtil.fromAngle(in.readByte());
	}

	@Override
	public void write(PacketOutSetHeadRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeByte(MathUtil.toAngle(packet.yaw));
	}
	
	@Override
	public PacketOutSetHeadRotation createPacketData() {
		return new PacketOutSetHeadRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetHeadRotation.class);
	}

}
