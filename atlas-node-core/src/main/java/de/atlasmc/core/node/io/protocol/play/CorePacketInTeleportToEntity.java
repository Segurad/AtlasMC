package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInTeleportToEntity;
import io.netty.buffer.ByteBuf;

public class CorePacketInTeleportToEntity implements PacketIO<PacketInTeleportToEntity> {
	
	@Override
	public void read(PacketInTeleportToEntity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		packet.uuid = new UUID(most, least);
	}

	@Override
	public void write(PacketInTeleportToEntity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		UUID uuid = packet.uuid;
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
	}

	@Override
	public PacketInTeleportToEntity createPacketData() {
		return new PacketInTeleportToEntity();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInTeleportToEntity.class);
	}

}
