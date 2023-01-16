package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.block.tile.CommandBlock.Mode;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateCommandBlock extends PacketIO<PacketInUpdateCommandBlock> {

	@Override
	public void read(PacketInUpdateCommandBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setCommand(readString(in));
		packet.setMode(Mode.getByID(readVarInt(in)));
		packet.setFlags(in.readByte());
	}

	@Override
	public void write(PacketInUpdateCommandBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeString(packet.getCommand(), out);
		writeVarInt(packet.getMode().getID(), out);
		out.writeByte(packet.getFlags());
	}

	@Override
	public PacketInUpdateCommandBlock createPacketData() {
		return new PacketInUpdateCommandBlock();
	}

}
