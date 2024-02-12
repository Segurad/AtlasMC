package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateTime;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateTime implements PacketIO<PacketOutUpdateTime> {

	@Override
	public void read(PacketOutUpdateTime packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWorldAge(in.readLong());
		packet.setTimeOfDay(in.readLong());
	}

	@Override
	public void write(PacketOutUpdateTime packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getWorldAge());
		out.writeLong(packet.getTimeOfDay());
	}
	
	@Override
	public PacketOutUpdateTime createPacketData() {
		return new PacketOutUpdateTime();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateTime.class);
	}

}
