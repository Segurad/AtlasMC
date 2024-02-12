package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInMessageAcknowledgment;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.writeVarInt;
import static de.atlasmc.io.AbstractPacket.readVarInt;

public class CorePacketInMessageAcknowledgment implements PacketIO<PacketInMessageAcknowledgment> {

	@Override
	public void read(PacketInMessageAcknowledgment packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);		
	}

	@Override
	public void write(PacketInMessageAcknowledgment packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
	}

	@Override
	public PacketInMessageAcknowledgment createPacketData() {
		return new PacketInMessageAcknowledgment();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInMessageAcknowledgment.class);
	}

}
