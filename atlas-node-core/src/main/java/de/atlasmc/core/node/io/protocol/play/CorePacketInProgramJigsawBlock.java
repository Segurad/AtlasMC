package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInProgramJigsawBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramJigsawBlock implements PacketIO<PacketInProgramJigsawBlock> {

	@Override
	public void read(PacketInProgramJigsawBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.name = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
		packet.target = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
		packet.pool = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
		packet.finalState = StringCodec.readString(in);
		packet.jointtype = StringCodec.readString(in);
		packet.selectionPriority = readVarInt(in);
		packet.placementPriority = readVarInt(in);
	}

	@Override
	public void write(PacketInProgramJigsawBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		NamespacedKey.STREAM_CODEC.serialize(packet.name, out, null);
		NamespacedKey.STREAM_CODEC.serialize(packet.target, out, null);
		NamespacedKey.STREAM_CODEC.serialize(packet.pool, out, null);
		StringCodec.writeString(packet.finalState, out);
		StringCodec.writeString(packet.jointtype, out);
		writeVarInt(packet.selectionPriority, out);
		writeVarInt(packet.placementPriority, out);
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
