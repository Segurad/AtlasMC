package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketInLoginStart;
import io.netty.buffer.ByteBuf;

public class CorePacketInLoginStart extends AbstractPacket implements PacketInLoginStart {

	private String name;
	
	public CorePacketInLoginStart() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		name = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(name, out);
	}

	@Override
	public String getName() {
		return name;
	}

}
