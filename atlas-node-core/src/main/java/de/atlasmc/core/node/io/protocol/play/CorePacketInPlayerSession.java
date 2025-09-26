package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPlayerSession;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerSession implements PacketIO<PacketInPlayerSession> {

	@Override
	public void read(PacketInPlayerSession packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.sessionID = readUUID(in);
		packet.expiresAt = in.readLong();
		int keySize = readVarInt(in);
		byte[] key = packet.publicKey = new byte[keySize];
		in.readBytes(key);
		int signatureSize = readVarInt(in);
		byte[] signature = packet.keySignature = new byte[signatureSize];
		in.readBytes(signature);
	}

	@Override
	public void write(PacketInPlayerSession packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeUUID(packet.sessionID, out);
		out.writeLong(packet.expiresAt);
		writeVarInt(packet.publicKey.length, out);
		out.writeBytes(packet.publicKey);
		writeVarInt(packet.keySignature.length, out);
		out.writeBytes(packet.keySignature);
	}

	@Override
	public PacketInPlayerSession createPacketData() {
		return new PacketInPlayerSession();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerSession.class);
	}

}
