package de.atlascore.io.protocol.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutFeatureFlags;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutFeatureFlags implements PacketIO<PacketOutFeatureFlags> {

	@Override
	public void read(PacketOutFeatureFlags packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int size = readVarInt(in);
		if (size <= 0)
			return;
		List<NamespacedKey> flags = packet.flags = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			flags.add(readIdentifier(in));
		}
	}

	@Override
	public void write(PacketOutFeatureFlags packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<NamespacedKey> flags = packet.flags;
		if (flags == null || flags.isEmpty()) {
			writeVarInt(0, out);
		} else {
			writeVarInt(flags.size(), out);
			for (NamespacedKey key : flags)
				writeIdentifier(key, out);
		}
	}

	@Override
	public PacketOutFeatureFlags createPacketData() {
		return new PacketOutFeatureFlags();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutFeatureFlags.class);
	}

}
