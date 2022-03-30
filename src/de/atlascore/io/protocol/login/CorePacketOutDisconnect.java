package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketOutDisconnect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisconnect extends AbstractPacket implements PacketOutDisconnect {

	private String reason;
	
	public CorePacketOutDisconnect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutDisconnect(Chat reason) {
		this();
		this.reason = reason.getJsonText();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		reason = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(reason, out);
	}

	@Override
	public Chat getReason() {
		return ChatUtil.toChat(reason);
	}

}
