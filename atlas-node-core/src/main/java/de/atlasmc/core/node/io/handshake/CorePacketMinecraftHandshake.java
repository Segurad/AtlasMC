package de.atlasmc.core.node.io.handshake;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.event.HandlerList;
import de.atlasmc.io.Packet;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.io.protocol.handshake.HandshakePacketCodec;
import de.atlasmc.node.event.socket.AsyncPlayerHandshakeEvent;
import de.atlasmc.node.io.protocol.handshake.HandshakeData;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftHandshake;
import io.netty.buffer.ByteBuf;

public class CorePacketMinecraftHandshake extends HandshakePacketCodec<PacketMinecraftHandshake> {

	@Override
	public void handle(ConnectionHandler handler, PacketMinecraftHandshake packet) {
		HandlerList.callEvent(
				new AsyncPlayerHandshakeEvent(
						true, 
						(ServerSocketConnectionHandler) handler, 
						new HandshakeData(
								packet.getTimestamp(), 
								packet.protocolVersion, 
								packet.address, 
								packet.port, 
								packet.nextState)));
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
