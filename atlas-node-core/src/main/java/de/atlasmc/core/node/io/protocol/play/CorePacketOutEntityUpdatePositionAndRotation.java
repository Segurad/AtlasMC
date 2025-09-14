package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateEntityPositionAndRotation;
import de.atlasmc.node.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityUpdatePositionAndRotation implements PacketIO<PacketOutUpdateEntityPositionAndRotation> {
	
	@Override
	public void read(PacketOutUpdateEntityPositionAndRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.deltaX = in.readShort();
		packet.deltaY = in.readShort();
		packet.deltaZ = in.readShort();
		packet.yaw = MathUtil.fromAngle(in.readUnsignedByte());
		packet.pitch = MathUtil.fromAngle(in.readUnsignedByte());
		packet.onGround = in.readBoolean();
	}

	@Override
	public void write(PacketOutUpdateEntityPositionAndRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeShort(packet.deltaX);
		out.writeShort(packet.deltaY);
		out.writeShort(packet.deltaZ);
		out.writeByte(MathUtil.toAngle(packet.yaw));
		out.writeByte(MathUtil.toAngle(packet.pitch));
		out.writeBoolean(packet.onGround);
	}

	@Override
	public PacketOutUpdateEntityPositionAndRotation createPacketData() {
		return new PacketOutUpdateEntityPositionAndRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateEntityPositionAndRotation.class);
	}

}
