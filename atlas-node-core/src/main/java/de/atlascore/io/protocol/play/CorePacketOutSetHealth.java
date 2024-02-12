package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateHealth;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetHealth implements PacketIO<PacketOutUpdateHealth> {

	@Override
	public void read(PacketOutUpdateHealth packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHealth(in.readFloat());
		packet.setFood(readVarInt(in));
		packet.setSaturation(in.readFloat());
	}

	@Override
	public void write(PacketOutUpdateHealth packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.getHealth());
		writeVarInt(packet.getFood(), out);
		out.writeFloat(packet.getSaturation());
	}

	@Override
	public PacketOutUpdateHealth createPacketData() {
		return new PacketOutUpdateHealth();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateHealth.class);
	}

}
