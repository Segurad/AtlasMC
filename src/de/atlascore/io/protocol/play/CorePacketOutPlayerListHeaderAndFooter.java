package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutPlayerListHeaderAndFooter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerListHeaderAndFooter extends AbstractPacket implements PacketOutPlayerListHeaderAndFooter {

	private String header, footer;
	
	public CorePacketOutPlayerListHeaderAndFooter() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPlayerListHeaderAndFooter(Chat header, Chat footer) {
		this();
		if (header == null) this.header = ChatUtil.EMPTY.getText();
		else this.header = header.getText();
		if (footer == null) this.footer = ChatUtil.EMPTY.getText();
		else this.footer = footer.getText();
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
	public Chat getHeader() {
		return ChatUtil.toChat(header);
	}

	@Override
	public Chat getFooter() {
		return ChatUtil.toChat(footer);
	}

}
