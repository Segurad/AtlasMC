package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInUpdateJigsawBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateJigsawBlock extends CoreAbstractHandler<PacketInUpdateJigsawBlock> {

	@Override
	public void read(PacketInUpdateJigsawBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setName(readString(in));
		packet.setTarget(readString(in));
		packet.setPool(readString(in));
		packet.setFinalState(readString(in));
		packet.setJointtype(readString(in));
	}

	@Override
	public void write(PacketInUpdateJigsawBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeString(packet.getName(), out);
		writeString(packet.getTarget(), out);
		writeString(packet.getPool(), out);
		writeString(packet.getFinalState(), out);
		writeString(packet.getJointtype(), out);
	}

	@Override
	public PacketInUpdateJigsawBlock createPacketData() {
		return new PacketInUpdateJigsawBlock();
	}

}
