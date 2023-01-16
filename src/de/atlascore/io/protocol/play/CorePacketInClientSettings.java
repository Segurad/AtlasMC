package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.chat.ChatType;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClientSettings;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientSettings extends PacketIO<PacketInClientSettings> {

	@Override
	public void read(PacketInClientSettings packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setLocale(readString(in));
		packet.setViewDistance(in.readByte());
		packet.setChatMode(ChatType.getByID(readVarInt(in)));
		packet.setChatColor(in.readBoolean());
		packet.setDisplaySkinParts(in.readByte());
		packet.setMainHand(readVarInt(in));
	}

	@Override
	public void write(PacketInClientSettings packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.getLocale(), out);
		out.writeByte(packet.getViewDistance());
		writeVarInt(packet.getChatMode().getID(), out);
		out.writeBoolean(packet.getChatColor());
		out.writeByte(packet.getDisplaySkinParts());
		writeVarInt(packet.getMainHand(), out);
	}
	
	@Override
	public PacketInClientSettings createPacketData() {
		return new PacketInClientSettings();
	}

}
