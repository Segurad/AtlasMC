package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.Difficulty;
import de.atlasmc.node.io.protocol.play.PacketOutChangeDifficulty;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChangeDifficulty implements PacketIO<PacketOutChangeDifficulty> {

	@Override
	public void read(PacketOutChangeDifficulty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.difficulty = EnumUtil.getByID(Difficulty.class, in.readUnsignedByte());
		packet.locked = in.readBoolean();
	}

	@Override
	public void write(PacketOutChangeDifficulty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.difficulty.getID());
		out.writeBoolean(packet.locked);
	}

	@Override
	public PacketOutChangeDifficulty createPacketData() {
		return new PacketOutChangeDifficulty();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChangeDifficulty.class);
	}
	
}
