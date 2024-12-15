package de.atlascore.io.protocol.common;

import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.common.AbstractPacketKnownPacks;
import de.atlasmc.io.protocol.common.AbstractPacketKnownPacks.PackInfo;
import de.atlasmc.io.protocol.configuration.PacketOutKnownPacks;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketKnownPacks<T extends AbstractPacketKnownPacks> implements PacketIO<T> {
	
	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		ArrayList<PackInfo> packs = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			PackInfo pack = new PackInfo();
			pack.namespace = readString(in);
			pack.id = readString(in);
			pack.version = readString(in);
			packs.add(pack);
		}
		packet.knownPacks = packs;
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<PackInfo> packs = packet.knownPacks;
		final int count = packs.size();
		writeVarInt(count, out);
		for (int i = 0; i < count; i++) {
			PackInfo pack = packs.get(i);
			writeString(pack.namespace, out);
			writeString(pack.id, out);
			writeString(pack.version, out);
		}
	}

}
