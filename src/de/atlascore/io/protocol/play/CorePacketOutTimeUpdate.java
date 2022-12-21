package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutTimeUpdate;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTimeUpdate extends CoreAbstractHandler<PacketOutTimeUpdate> {

	@Override
	public void read(PacketOutTimeUpdate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWorldAge(in.readLong());
		packet.setTimeOfDay(in.readLong());
	}

	@Override
	public void write(PacketOutTimeUpdate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getWorldAge());
		out.writeLong(packet.getTimeOfDay());
	}
	
	@Override
	public PacketOutTimeUpdate createPacketData() {
		return new PacketOutTimeUpdate();
	}

}
