package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import io.netty.buffer.ByteBuf;

public class PacketInChatMessageV1_16_3 extends AbstractPacket implements PacketInChatMessage {

	public PacketInChatMessageV1_16_3() {
		super(0x03, V1_16_3.version);
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
