package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInSpectate;
import io.netty.buffer.ByteBuf;

public class CorePacketInSpectate extends CoreAbstractHandler<PacketInSpectate> {
	
	@Override
	public void read(PacketInSpectate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		packet.setUUID(new UUID(most, least));
	}

	@Override
	public void write(PacketInSpectate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		UUID uuid = packet.getUUID();
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
	}

	@Override
	public PacketInSpectate createPacketData() {
		return new PacketInSpectate();
	}

}
