package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSpectate;
import io.netty.buffer.ByteBuf;

public class CorePacketInSpectate extends AbstractPacket implements PacketInSpectate {

	private UUID uuid;
	private String suuid;
	
	public CorePacketInSpectate() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		suuid = readString(in);
		uuid = UUID.fromString(suuid);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(suuid, out);
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

}
