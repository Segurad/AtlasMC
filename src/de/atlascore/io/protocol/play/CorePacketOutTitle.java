package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.FinalComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutTitle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTitle extends AbstractPacket implements PacketOutTitle {

	private int action, fadeIn, stay, fadeOut;
	private String title;
	
	public CorePacketOutTitle() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutTitle(TitleAction action, ChatComponent title, int fadeIn, int stay, int fadeOut) {
		this();
		this.action = action.ordinal();
		this.title = title.getJsonText();
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		action = readVarInt(in);
		switch (action) {
		case 0:
		case 1:
		case 2:
			title = readString(in);
			break;
		case 3:
			fadeIn = in.readInt();
			stay = in.readInt();
			fadeOut = in.readInt();
			break;
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(action, out);
		switch (action) {
		case 0:
		case 1:
		case 2:
			writeString(title, out);
			break;
		case 3:
			out.writeInt(fadeIn);
			out.writeInt(stay);
			out.writeInt(fadeOut);
		}
	}

	@Override
	public TitleAction getAction() {
		return TitleAction.getByID(action);
	}

	@Override
	public ChatComponent getText() {
		return new FinalComponent(title);
	}

	@Override
	public int getFadeIn() {
		return fadeIn;
	}

	@Override
	public int getStay() {
		return stay;
	}

	@Override
	public int getFadeOut() {
		return fadeOut;
	}

}
