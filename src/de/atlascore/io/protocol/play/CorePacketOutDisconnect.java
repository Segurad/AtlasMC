package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDisconnect;
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
	public String getReason() {
		return reason;
	}

}
