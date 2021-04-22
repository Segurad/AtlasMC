package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInChatMessage extends AbstractPacket implements PacketInChatMessage {

	public CorePacketInChatMessage() {
		super(CoreProtocolAdapter.VERSION);
	}

	private String msg;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		msg = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(msg, out);
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
