package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutTeleportEntity;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTeleportEntity implements PacketIO<PacketOutTeleportEntity> {

	@Override
	public void read(PacketOutTeleportEntity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.velocityX = in.readDouble();
		packet.velocityY = in.readDouble();
		packet.velocityZ = in.readDouble();
		packet.yaw = MathUtil.fromAngle(in.readUnsignedByte());
		packet.pitch = MathUtil.fromAngle(in.readUnsignedByte());
		packet.onGround = in.readBoolean();
	}

	@Override
	public void write(PacketOutTeleportEntity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeDouble(packet.velocityX);
		out.writeDouble(packet.velocityY);
		out.writeDouble(packet.velocityZ);
		out.writeByte(MathUtil.toAngle(packet.yaw));
		out.writeByte(MathUtil.toAngle(packet.pitch));
		out.writeBoolean(packet.onGround);
	}
	
	@Override
	public PacketOutTeleportEntity createPacketData() {
		return new PacketOutTeleportEntity();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutTeleportEntity.class);
	}

}
