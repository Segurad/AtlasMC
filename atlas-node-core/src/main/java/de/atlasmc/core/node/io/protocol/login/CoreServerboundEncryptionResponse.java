package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ServerboundEncryptionResponse;
import io.netty.buffer.ByteBuf;

public class CoreServerboundEncryptionResponse implements PacketIO<ServerboundEncryptionResponse> {

	@Override
	public void read(ServerboundEncryptionResponse packet, ByteBuf in, ConnectionHandler con) throws IOException {
		int secretLength = readVarInt(in);
		byte[] secret = packet.secret = new byte[secretLength];
		in.readBytes(secret);
		int tokenLength = readVarInt(in);
		byte[] token = packet.verifyToken = new byte[tokenLength];
		in.readBytes(token);
	}

	@Override
	public void write(ServerboundEncryptionResponse packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.secret.length, out);
		out.writeBytes(packet.secret);
		writeVarInt(packet.verifyToken.length, out);
		out.writeBytes(packet.verifyToken);
	}

	@Override
	public ServerboundEncryptionResponse createPacketData() {
		return new ServerboundEncryptionResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundEncryptionResponse.class);
	}

}
