package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnEntity implements PacketIO<PacketOutSpawnEntity> {

	@Override
	public void read(PacketOutSpawnEntity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		long most = in.readLong();
		long least = in.readLong();
		packet.uuid = new UUID(most, least);
		packet.type = EntityType.getByID(readVarInt(in));
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.yaw = MathUtil.fromAngle(in.readUnsignedByte());
		packet.pitch = MathUtil.fromAngle(in.readUnsignedByte());
		packet.headYaw = MathUtil.fromAngle(in.readUnsignedByte());
		packet.objectdata = in.readInt();
		packet.velocityX = in.readShort()/8000;
		packet.velocityY = in.readShort()/8000;
		packet.velocityZ = in.readShort()/8000;
	}

	@Override
	public void write(PacketOutSpawnEntity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		UUID uuid = packet.uuid;
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(packet.type.getTypeID(), out);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeByte(MathUtil.toAngle(packet.yaw));
		out.writeByte(MathUtil.toAngle(packet.pitch));
		out.writeByte(MathUtil.toAngle(packet.headYaw));
		out.writeInt(packet.objectdata);
		out.writeShort((int) (packet.velocityX*8000));
		out.writeShort((int) (packet.velocityY*8000));
		out.writeShort((int) (packet.velocityZ*8000));
	}
	
	@Override
	public PacketOutSpawnEntity createPacketData() {
		return new PacketOutSpawnEntity();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSpawnEntity.class);
	}

}
