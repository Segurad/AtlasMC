package de.atlasmc.core.node.io.handshake;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.protocol.handshake.HandshakePacketCodec;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.io.protocol.ProtocolAdapter;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftHandshake;
import io.netty.buffer.ByteBuf;

public class CorePacketMinecraftHandshake extends HandshakePacketCodec<PacketMinecraftHandshake> {

	@Override
	public void handle(ConnectionHandler handler, PacketMinecraftHandshake packet) {
		ProtocolAdapter adapter = AtlasNode.getProtocolAdapter(packet.protocolVersion);
		if (adapter == null) {
			handler.getLogger().debug("No Protocol with found with version: {}", packet.protocolVersion);
			handler.close();
			return;
		}
		final int nextState = packet.nextState;
		final Protocol prot = switch (nextState) {
		case 1 -> adapter.getStatusProtocol();
		case 2 -> adapter.getLoginProtocol();
		default -> throw new ProtocolException("Invalid id for next protocol state: " + nextState);
		};
		handler.setProtocol(prot);
		handler.getInboundListeners().addFirst("default", prot.createDefaultPacketListenerServerbound(prot));
	}

	@Override
	public void deserialize(PacketMinecraftHandshake packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.protocolVersion = readVarInt(in);
		packet.address = StringCodec.readString(in, 255);
		packet.port = in.readUnsignedShort();
		packet.nextState = readVarInt(in);
	}

	@Override
	public void serialize(PacketMinecraftHandshake packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.protocolVersion, out);
		StringCodec.writeString(packet.address, out);
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
