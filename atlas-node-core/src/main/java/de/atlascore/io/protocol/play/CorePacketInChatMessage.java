package de.atlascore.io.protocol.play;

import java.io.IOException;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInChatMessage implements PacketIO<PacketInChatMessage> {

	@Override
	public void read(PacketInChatMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setMessage(readString(in, 256));
		packet.setMessageTimestamp(in.readLong());
		packet.setSalt(in.readLong());
		boolean hasSignature = in.readBoolean();
		if (hasSignature) {
			byte[] signature = new byte[256];
			in.readBytes(signature);
			packet.setSignature(signature);
		}
		packet.setMessageCount(readVarInt(in));
		packet.setAcknowledged(readBitSet(in, 20));
	}

	@Override
	public void write(PacketInChatMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.getMessage(), out);
		out.writeLong(packet.getMessageTimestamp());
		out.writeLong(packet.getSalt());
		byte[] signature = packet.getSignature();
		if (signature != null) {
			out.writeBoolean(true);
			out.writeBytes(signature);
		} else {
			out.writeBoolean(false);
		}
		writeVarInt(packet.getMessageCount(), out);
		writeBitSet(packet.getAcknowledged(), out);
	}
	
	@Override
	public PacketInChatMessage createPacketData() {
		return new PacketInChatMessage();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChatMessage.class);
	}

}
