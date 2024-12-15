package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.block.tile.CommandBlock.Mode;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInProgramCommandBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramCommandBlock implements PacketIO<PacketInProgramCommandBlock> {

	@Override
	public void read(PacketInProgramCommandBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.command = readString(in, 32767);
		packet.mode = Mode.getByID(readVarInt(in));
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketInProgramCommandBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		writeString(packet.command, out);
		writeVarInt(packet.mode.getID(), out);
		out.writeByte(packet.flags);
	}

	@Override
	public PacketInProgramCommandBlock createPacketData() {
		return new PacketInProgramCommandBlock();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInProgramCommandBlock.class);
	}

}
