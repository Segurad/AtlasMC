package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.block.tile.CommandBlock.Mode;
import de.atlasmc.node.io.protocol.play.PacketInProgramCommandBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramCommandBlock implements PacketCodec<PacketInProgramCommandBlock> {

	@Override
	public void deserialize(PacketInProgramCommandBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.command = StringCodec.readString(in);
		packet.mode = Mode.getByID(readVarInt(in));
		packet.flags = in.readByte();
	}

	@Override
	public void serialize(PacketInProgramCommandBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		StringCodec.writeString(packet.command, out);
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
