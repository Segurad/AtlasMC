package de.atlascore.io.handshake;

import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.AtlasNode;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.handshake.HandshakePaketIO;
import de.atlasmc.io.handshake.PacketMinecraftHandshake;
import de.atlasmc.io.protocol.ProtocolAdapter;
import io.netty.buffer.ByteBuf;

public class CorePacketMinecraftHandshake extends HandshakePaketIO<PacketMinecraftHandshake> {

	@Override
	public void handle(ConnectionHandler handler, PacketMinecraftHandshake packet) {
		ProtocolAdapter adapter = AtlasNode.getProtocolAdapter(packet.protocolVersion);
		if (adapter == null) {
			handler.close();
			return;
		}
		final int nextState = packet.nextState;
		if (nextState == 1) {
			final Protocol prot = adapter.getStatusProtocol();
			handler.setProtocol(prot, prot.createDefaultPacketListener(handler));
		} else if (nextState == 2) {
			final Protocol prot = adapter.getLoginProtocol();
			handler.setProtocol(prot, prot.createDefaultPacketListener(handler));
		} else {
			throw new ProtocolException("Invalid id for next protocol state: " + nextState);
		}
	}

	@Override
	public void read(PacketMinecraftHandshake packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.protocolVersion = readVarInt(in);
		packet.address = readString(in, 255);
		packet.port = in.readUnsignedShort();
		packet.nextState = readVarInt(in);
	}

	@Override
	public void write(PacketMinecraftHandshake packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.protocolVersion, out);
		writeString(packet.address, out);
		out.writeShort(packet.port);
		writeVarInt(packet.nextState, out);
	}

	@Override
	public PacketMinecraftHandshake createPacketData() {
		return new PacketMinecraftHandshake();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketMinecraftHandshake.class);
	}

}
