package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutTitle;
import de.atlasmc.io.protocol.play.PacketOutTitle.TitleAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTitle extends PacketIO<PacketOutTitle> {

	@Override
	public void read(PacketOutTitle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		TitleAction action = TitleAction.getByID(readVarInt(in));
		packet.setAction(action);
		switch (action) {
		case SET_TITLE:
		case SET_SUBTITLE:
		case SET_ACTION_BAR:
			packet.setTitle(readString(in));
			break;
		case SET_TIME_AND_DISPLAY:
			packet.setFadeIn(in.readInt());
			packet.setStay(in.readInt());
			packet.setFadeOut(in.readInt());
			break;
		default:
			break;
		}
	}

	@Override
	public void write(PacketOutTitle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getAction().getID(), out);
		switch (packet.getAction()) {
		case SET_TITLE:
		case SET_SUBTITLE:
		case SET_ACTION_BAR:
			writeString(packet.getTitle(), out);
			break;
		case SET_TIME_AND_DISPLAY:
			out.writeInt(packet.getFadeIn());
			out.writeInt(packet.getStay());
			out.writeInt(packet.getFadeOut());
		default:
			break;
		}
	}

	@Override
	public PacketOutTitle createPacketData() {
		return new PacketOutTitle();
	}

}
