package de.atlascore.io.protocol.login;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketOutEncryptionRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEncryptionRequest implements PacketIO<PacketOutEncryptionRequest> {

	@Override
	public void read(PacketOutEncryptionRequest packet, ByteBuf in, ConnectionHandler handler) throws IOException {
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
	public void write(PacketOutEncryptionRequest packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.serverID, out);
		writeVarInt(packet.publicKey.length, out);
		out.writeBytes(packet.publicKey);
		writeVarInt(packet.verifyToken.length, out);
		out.writeBytes(packet.verifyToken);
		out.writeBoolean(packet.authenticate);
	}

	@Override
	public PacketOutEncryptionRequest createPacketData() {
		return new PacketOutEncryptionRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEncryptionRequest.class);
	}

}
