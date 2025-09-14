package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateEntityRotation;
import de.atlasmc.node.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateEntityRotation implements PacketIO<PacketOutUpdateEntityRotation> {

	@Override
	public void read(PacketOutUpdateEntityRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.yaw = MathUtil.fromAngle(in.readUnsignedByte());
		packet.pitch = MathUtil.fromAngle(in.readUnsignedByte());
		packet.onGround = in.readBoolean();
	}

	@Override
	public void write(PacketOutUpdateEntityRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeByte(MathUtil.toAngle(packet.yaw));
		out.writeByte(MathUtil.toAngle(packet.pitch));
		out.writeBoolean(packet.onGround);
	}

	@Override
	public PacketOutUpdateEntityRotation createPacketData() {
		return new PacketOutUpdateEntityRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateEntityRotation.class);
	}

}
