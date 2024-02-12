package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInProgramJigsawBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramJigsawBlock implements PacketIO<PacketInProgramJigsawBlock> {

	@Override
	public void read(PacketInProgramJigsawBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setName(readString(in));
		packet.setTarget(readString(in));
		packet.setPool(readString(in));
		packet.setFinalState(readString(in));
		packet.setJointtype(readString(in));
	}

	@Override
	public void write(PacketInProgramJigsawBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeString(packet.getName(), out);
		writeString(packet.getTarget(), out);
		writeString(packet.getPool(), out);
		writeString(packet.getFinalState(), out);
		writeString(packet.getJointtype(), out);
	}

	@Override
	public PacketInProgramJigsawBlock createPacketData() {
		return new PacketInProgramJigsawBlock();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInProgramJigsawBlock.class);
	}

}
