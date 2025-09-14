package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.login.PacketInEncryptionResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketInEncryptionResponse implements PacketIO<PacketInEncryptionResponse> {

	@Override
	public void read(PacketInEncryptionResponse packet, ByteBuf in, ConnectionHandler con) throws IOException {
		int secretLength = readVarInt(in);
		byte[] secret = packet.secret = new byte[secretLength];
		in.readBytes(secret);
		int tokenLength = readVarInt(in);
		byte[] token = packet.verifyToken = new byte[tokenLength];
		in.readBytes(token);
	}

	@Override
	public void write(PacketInEncryptionResponse packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.secret.length, out);
		out.writeBytes(packet.secret);
		writeVarInt(packet.verifyToken.length, out);
		out.writeBytes(packet.verifyToken);
	}

	@Override
	public PacketInEncryptionResponse createPacketData() {
		return new PacketInEncryptionResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInEncryptionResponse.class);
	}

}
