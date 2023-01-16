package de.atlascore.io.protocol.login;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketOutEncryptionRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEncryptionRequest extends PacketIO<PacketOutEncryptionRequest> {

	@Override
	public void read(PacketOutEncryptionRequest packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setServerID(readString(in));
		int length = readVarInt(in);
		byte[] publicKey = new byte[length];
		in.readBytes(publicKey);
		packet.setPublicKey(publicKey);
		length = readVarInt(in);
		byte[] verifyToken = new byte[length];
		in.readBytes(verifyToken);
		packet.setVerifyToken(verifyToken);
	}

	@Override
	public void write(PacketOutEncryptionRequest packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getServerID(), out);
		writeVarInt(packet.getPublicKey().length, out);
		out.writeBytes(packet.getPublicKey());
		writeVarInt(packet.getVerifyToken().length, out);
		out.writeBytes(packet.getVerifyToken());
	}

	@Override
	public PacketOutEncryptionRequest createPacketData() {
		return new PacketOutEncryptionRequest();
	}

}
