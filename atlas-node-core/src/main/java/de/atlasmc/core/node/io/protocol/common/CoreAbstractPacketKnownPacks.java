package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketKnownPacks;
import de.atlasmc.node.io.protocol.common.AbstractPacketKnownPacks.PackInfo;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketKnownPacks<T extends AbstractPacketKnownPacks> implements PacketIO<T> {
	
	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		ArrayList<PackInfo> packs = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			PackInfo pack = new PackInfo();
			pack.namespace = StringCodec.readString(in);
			pack.id = StringCodec.readString(in);
			pack.version = StringCodec.readString(in);
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
			StringCodec.writeString(pack.namespace, out);
			StringCodec.writeString(pack.id, out);
			StringCodec.writeString(pack.version, out);
		}
	}

}
