package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutWorldEvent;
import de.atlasmc.world.WorldEvent;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWorldEvent implements PacketIO<PacketOutWorldEvent> {

	@Override
	public void read(PacketOutWorldEvent packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.event = WorldEvent.getByID(in.readInt());
		packet.position = in.readLong();
		packet.data = in.readInt();
		packet.disableRelativeVolume = in.readBoolean();
	}

	@Override
	public void write(PacketOutWorldEvent packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.event.getID());
		out.writeLong(packet.position);
		out.writeInt(packet.data);
		out.writeBoolean(packet.disableRelativeVolume);
	}
	
	@Override
	public PacketOutWorldEvent createPacketData() {
		return new PacketOutWorldEvent();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutWorldEvent.class);
	}

}
