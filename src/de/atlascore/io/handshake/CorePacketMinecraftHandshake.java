package de.atlascore.io.handshake;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.Atlas;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.handshake.HandshakePaketIO;
import de.atlasmc.io.handshake.PacketMinecraftHandshake;
import de.atlasmc.io.protocol.ProtocolAdapter;
import io.netty.buffer.ByteBuf;

/**
 * Default Minecraft handshake
 */
@DefaultPacketID(0x00)
public class CorePacketMinecraftHandshake extends HandshakePaketIO<PacketMinecraftHandshake> {

	@Override
	public void handle(ConnectionHandler handler, PacketMinecraftHandshake packet) {
		ProtocolAdapter adapter = Atlas.getProtocolAdapter(packet.getProtocolVersion());
		if (adapter == null) {
			handler.close();
		}
		final int nextState = packet.getNextState();
		if (nextState == 1) {
			final Protocol prot = adapter.getStatusProtocol();
			handler.setProtocol(prot, prot.createDefaultPacketListener(handler));
		} else if (nextState == 2) {
			final Protocol prot = adapter.getLoginProtocol();
			handler.setProtocol(prot, prot.createDefaultPacketListener(handler));
		}
	}

	@Override
	public void read(PacketMinecraftHandshake packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setProtocolVersion(readVarInt(in));
		packet.setAddress(readString(in));
		packet.setPort(in.readShort());
		packet.setNextState(readVarInt(in));
	}

	@Override
	public void write(PacketMinecraftHandshake packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getProtocolVersion(), out);
		writeString(packet.getAddress(), out);
		out.writeShort(packet.getPort());
		writeVarInt(packet.getNextState(), out);
	}

	@Override
	public PacketMinecraftHandshake createPacketData() {
		return new PacketMinecraftHandshake();
	}

}
