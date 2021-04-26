package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.FinalComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketOutDisconnect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisconnect extends AbstractPacket implements PacketOutDisconnect {

	private String reason;
	
	public CorePacketOutDisconnect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutDisconnect(ChatComponent reason) {
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
	public ChatComponent getReason() {
		return new FinalComponent(reason);
	}

}
