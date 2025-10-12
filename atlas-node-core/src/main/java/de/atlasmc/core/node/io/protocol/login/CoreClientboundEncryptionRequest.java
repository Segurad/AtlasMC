package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ClientboundEncryptionRequest;
import io.netty.buffer.ByteBuf;

public class CoreClientboundEncryptionRequest implements PacketIO<ClientboundEncryptionRequest> {

	@Override
	public void read(ClientboundEncryptionRequest packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.serverID = readString(in);
		int length = readVarInt(in);
		byte[] publicKey = new byte[length];
		in.readBytes(publicKey);
		packet.publicKey = publicKey;
		length = readVarInt(in);
		byte[] verifyToken = new byte[length];
		in.readBytes(verifyToken);
		packet.verifyToken = verifyToken;
		packet.authenticate = in.readBoolean();
	}

	@Override
	public void write(ClientboundEncryptionRequest packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.serverID, out);
		writeVarInt(packet.publicKey.length, out);
		out.writeBytes(packet.publicKey);
		writeVarInt(packet.verifyToken.length, out);
		out.writeBytes(packet.verifyToken);
		out.writeBoolean(packet.authenticate);
	}

	@Override
	public ClientboundEncryptionRequest createPacketData() {
		return new ClientboundEncryptionRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundEncryptionRequest.class);
	}

}
