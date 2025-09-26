package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutTeleportVehicle;
import de.atlasmc.node.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTeleportVehicle implements PacketIO<PacketOutTeleportVehicle> {

	@Override
	public void read(PacketOutTeleportVehicle packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.velocityX = in.readDouble();
		packet.velocityY = in.readDouble();
		packet.velocityZ = in.readDouble();
		packet.yaw = MathUtil.fromAngle(in.readUnsignedByte());
		packet.pitch = MathUtil.fromAngle(in.readUnsignedByte());
		packet.flags = in.readInt();
		packet.onGround = in.readBoolean();
	}

	@Override
	public void write(PacketOutTeleportVehicle packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeDouble(packet.velocityX);
		out.writeDouble(packet.velocityY);
		out.writeDouble(packet.velocityZ);
		out.writeByte(MathUtil.toAngle(packet.yaw));
		out.writeByte(MathUtil.toAngle(packet.pitch));
		out.writeInt(packet.flags);
		out.writeBoolean(packet.onGround);
	}

	@Override
	public PacketOutTeleportVehicle createPacketData() {
		return new PacketOutTeleportVehicle();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutTeleportVehicle.class);
	}

}
