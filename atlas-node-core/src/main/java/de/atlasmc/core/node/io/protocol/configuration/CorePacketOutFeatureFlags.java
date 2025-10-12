package de.atlasmc.core.node.io.protocol.configuration;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.configuration.ClientboundFeatureFlags;
import io.netty.buffer.ByteBuf;

public class CorePacketOutFeatureFlags implements PacketIO<ClientboundFeatureFlags> {

	@Override
	public void read(ClientboundFeatureFlags packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int size = readVarInt(in);
		if (size <= 0)
			return;
		List<NamespacedKey> flags = packet.flags = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			flags.add(readIdentifier(in));
		}
	}

	@Override
	public void write(ClientboundFeatureFlags packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
	public ClientboundFeatureFlags createPacketData() {
		return new ClientboundFeatureFlags();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundFeatureFlags.class);
	}

}
