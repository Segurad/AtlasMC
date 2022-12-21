package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutSpawnPlayer;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnPlayer extends CoreAbstractHandler<PacketOutSpawnPlayer> {

	@Override
	public void read(PacketOutSpawnPlayer packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		long most = in.readLong();
		long least = in.readLong();
		packet.setUUID(new UUID(most, least));
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setPitch(MathUtil.fromAngle(in.readUnsignedByte()));
	}

	@Override
	public void write(PacketOutSpawnPlayer packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		UUID uuid = packet.getUUID();
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
		out.writeByte(MathUtil.toAngle(packet.getPitch()));
	}

	@Override
	public PacketOutSpawnPlayer createPacketData() {
		return new PacketOutSpawnPlayer();
	}
	
}
