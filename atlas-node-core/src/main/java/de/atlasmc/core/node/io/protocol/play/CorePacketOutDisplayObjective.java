package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutDisplayObjective;
import de.atlasmc.node.scoreboard.DisplaySlot;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisplayObjective implements PacketIO<PacketOutDisplayObjective> {

	@Override
	public void read(PacketOutDisplayObjective packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = EnumUtil.getByID(DisplaySlot.class, in.readByte());
		packet.objective = StringCodec.readString(in);
	}

	@Override
	public void write(PacketOutDisplayObjective packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.position.getID());
		StringCodec.writeString(packet.objective, out);
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
