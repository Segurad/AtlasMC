package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.MAX_IDENTIFIER_LENGTH;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readTextComponent;
import static de.atlasmc.io.PacketUtil.readUUID;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeTextComponent;
import static de.atlasmc.io.PacketUtil.writeUUID;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.common.AbstractPacketAddResourcePack;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketAddResourcePack<T extends AbstractPacketAddResourcePack> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.uuid = readUUID(in);
		packet.url = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.hash = readString(in, 40);
		packet.force = in.readBoolean();
		if (in.readBoolean()) {
			packet.promptMessage = readTextComponent(in);
		}
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeUUID(packet.uuid, out);
		writeString(packet.url, out);
		writeString(packet.hash, out);
		out.writeBoolean(packet.force);
		if (packet.promptMessage != null) {
			out.writeBoolean(true);
			writeTextComponent(packet.promptMessage, out);
		} else {
			out.writeBoolean(false);
		}
	}

}
