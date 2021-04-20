package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChatMessage extends AbstractPacket implements PacketOutChatMessage {
	
	private String chat;
	private UUID sender;
	private int position;
	
	public CorePacketOutChatMessage() {
		super(0x0E, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutChatMessage(ChatComponent chat, ChatMessage position, UUID sender) {
		this();
		if (position == ChatMessage.GANE_INFO) {
			this.chat = chat.getLegacyText();
		} else this.chat = chat.getJsonText();
		this.position = position.ordinal();
		this.sender = sender;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		chat = readString(in);
		position = in.readByte();
		long most = in.readLong();
		long least = in.readLong();
		sender = new UUID(most, least);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(chat, out);
		out.writeByte(position);
		if (sender == null) {
			out.writeLong(0);
			out.writeLong(0);
		} else {
			out.writeLong(sender.getMostSignificantBits());
			out.writeLong(sender.getLeastSignificantBits());
		}
	}
	

}
