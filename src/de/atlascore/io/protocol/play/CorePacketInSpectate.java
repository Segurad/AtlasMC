package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSpectate;
import io.netty.buffer.ByteBuf;

public class CorePacketInSpectate extends AbstractPacket implements PacketInSpectate {

	public CorePacketInSpectate() {
		super(0x2D, CoreProtocolAdapter.VERSION);
	}
	
	private String uuid;

	@Override
	public void read(ByteBuf in) throws IOException {
		uuid = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(uuid, out);
	}

	@Override
	public String getUUID() {
		return uuid;
	}

}
