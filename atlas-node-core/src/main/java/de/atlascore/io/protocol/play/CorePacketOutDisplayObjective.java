package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutDisplayObjective;
import de.atlasmc.scoreboard.DisplaySlot;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisplayObjective implements PacketIO<PacketOutDisplayObjective> {

	@Override
	public void read(PacketOutDisplayObjective packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(DisplaySlot.getByID(in.readByte()));
		packet.setObjective(readString(in, 32787));
	}

	@Override
	public void write(PacketOutDisplayObjective packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getPosition().getID());
		writeString(packet.getObjective(), out);
	}

	@Override
	public PacketOutDisplayObjective createPacketData() {
		return new PacketOutDisplayObjective();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDisplayObjective.class);
	}

}
