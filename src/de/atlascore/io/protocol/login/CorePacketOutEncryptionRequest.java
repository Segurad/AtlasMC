package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketOutEncryptionRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEncryptionRequest extends AbstractPacket implements PacketOutEncryptionRequest {

	private String serverID;
	private byte[] publicKey, verifyToken;
	
	public CorePacketOutEncryptionRequest() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEncryptionRequest(String serverID, byte[] publicKey, byte[] verifyToken) {
		this();
		this.serverID = serverID;
		this.publicKey = publicKey;
		this.verifyToken = verifyToken;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		serverID = readString(in);
		int length = readVarInt(in);
		publicKey = new byte[length];
		in.readBytes(publicKey);
		length = readVarInt(in);
		verifyToken = new byte[length];
		in.readBytes(verifyToken);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(serverID, out);
		writeVarInt(publicKey.length, out);
		out.writeBytes(publicKey);
		writeVarInt(verifyToken.length, out);
		out.writeBytes(verifyToken);
	}

	@Override
	public String getServerID() {
		return serverID;
	}

	@Override
	public byte[] getPublicKey() {
		return publicKey;
	}

	@Override
	public byte[] getVerifyToken() {
		return verifyToken;
	}

}
