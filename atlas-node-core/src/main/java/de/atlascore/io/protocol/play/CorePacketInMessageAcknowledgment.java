package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInAcknowledgeMessage;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInMessageAcknowledgment implements PacketIO<PacketInAcknowledgeMessage> {

	@Override
	public void read(PacketInAcknowledgeMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);		
	}

	@Override
	public void write(PacketInAcknowledgeMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
	}

	@Override
	public PacketInAcknowledgeMessage createPacketData() {
		return new PacketInAcknowledgeMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInAcknowledgeMessage.class);
	}

}
