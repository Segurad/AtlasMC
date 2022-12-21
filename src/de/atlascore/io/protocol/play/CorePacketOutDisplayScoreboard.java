package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutDisplayScoreboard;
import de.atlasmc.scoreboard.DisplaySlot;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisplayScoreboard extends CoreAbstractHandler<PacketOutDisplayScoreboard> {

	@Override
	public void read(PacketOutDisplayScoreboard packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(DisplaySlot.getByID(in.readByte()));
		packet.setObjective(readString(in));
	}

	@Override
	public void write(PacketOutDisplayScoreboard packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getPosition().getID());
		writeString(packet.getObjective(), out);
	}

	@Override
	public PacketOutDisplayScoreboard createPacketData() {
		return new PacketOutDisplayScoreboard();
	}

}
