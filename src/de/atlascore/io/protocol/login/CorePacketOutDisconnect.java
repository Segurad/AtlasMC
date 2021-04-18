package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketOutDisconnect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisconnect extends AbstractPacket implements PacketOutDisconnect {

	private String reason;
	
	public CorePacketOutDisconnect() {
		super(0x00, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutDisconnect(String reason) {
		this();
		this.reason = reason;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		reason = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(reason, out);
	}

}
