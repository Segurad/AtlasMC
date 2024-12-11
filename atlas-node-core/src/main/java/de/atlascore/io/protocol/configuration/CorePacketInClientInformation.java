package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketInClientInformation;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInClientInformation implements PacketIO<PacketInClientInformation> {

	@Override
	public void read(PacketInClientInformation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.local = readString(in, 16);
		packet.viewDistance = in.readByte();
		packet.chatMode = ChatMode.getByID(readVarInt(in));
		packet.chatColors = in.readBoolean();
		packet.skinParts = in.readUnsignedByte();
		packet.mainHand = readVarInt(in);
		packet.enableTextFiltering = in.readBoolean();
		packet.allowServerListing = in.readBoolean();
	}

	@Override
	public void write(PacketInClientInformation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.local, out);
		out.writeByte(packet.viewDistance);
		writeVarInt(packet.chatMode.getID(), out);
		out.writeBoolean(packet.chatColors);
		out.writeByte(packet.skinParts);
		writeVarInt(packet.mainHand, out);
		out.writeBoolean(packet.enableTextFiltering);
		out.writeBoolean(packet.allowServerListing);
	}

	@Override
	public PacketInClientInformation createPacketData() {
		return new PacketInClientInformation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInClientInformation.class);
	}

}
