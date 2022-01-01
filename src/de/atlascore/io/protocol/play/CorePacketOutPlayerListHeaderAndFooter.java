package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutPlayerListHeaderAndFooter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerListHeaderAndFooter extends AbstractPacket implements PacketOutPlayerListHeaderAndFooter {

	private String header, footer;
	private static final String clear = "{\"text\":\"\"}";
	
	public CorePacketOutPlayerListHeaderAndFooter() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPlayerListHeaderAndFooter(ChatComponent header, ChatComponent footer) {
		this();
		if (header == null) this.header = clear;
		else this.header = header.getJsonText();
		if (footer == null) this.footer = clear;
		else this.footer = footer.getJsonText();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		header = readString(in);
		footer = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(header, out);
		writeString(footer, out);
	}

	@Override
	public ChatComponent getHeader() {
		return ChatUtil.toChat(header);
	}

	@Override
	public ChatComponent getFooter() {
		return ChatUtil.toChat(footer);
	}

}
