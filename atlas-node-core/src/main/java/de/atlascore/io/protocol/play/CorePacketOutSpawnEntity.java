package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnEntity implements PacketIO<PacketOutSpawnEntity> {

	@Override
	public void read(PacketOutSpawnEntity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		long most = in.readLong();
		long least = in.readLong();
		packet.setUUID(new UUID(most, least));
		packet.setType(EntityType.getByID(readVarInt(in)));
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setPitch(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setHeadYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setObjectdata(in.readInt());
		packet.setVelocityX(in.readShort()/8000);
		packet.setVelocityY(in.readShort()/8000);
		packet.setVelocityZ(in.readShort()/8000);
	}

	@Override
	public void write(PacketOutSpawnEntity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		UUID uuid = packet.getUUID();
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(packet.getType().getTypeID(), out);
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
		out.writeByte(MathUtil.toAngle(packet.getPitch()));
		out.writeByte(MathUtil.toAngle(packet.getHeadYaw()));
		out.writeInt(packet.getObjectdata());
		out.writeShort((int) (packet.getVelocityX()*8000));
		out.writeShort((int) (packet.getVelocityY()*8000));
		out.writeShort((int) (packet.getVelocityZ()*8000));
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
