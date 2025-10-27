package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoRemove;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerInfoRemove implements PacketIO<PacketOutPlayerInfoRemove> {

	@Override
	public void read(PacketOutPlayerInfoRemove packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count <= 0)
			return;
		List<UUID> players = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
			players.add(readUUID(in));
		packet.players = players;
	}

	@Override
	public void write(PacketOutPlayerInfoRemove packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<UUID> players = packet.players;
		if (players == null || players.isEmpty()) {
			writeVarInt(0, out);
		} else {
			for (UUID uuid : players) {
				writeUUID(uuid, out);
			}
		}
	}

	@Override
	public PacketOutPlayerInfoRemove createPacketData() {
		return new PacketOutPlayerInfoRemove();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPlayerInfoRemove.class);
	}

}
