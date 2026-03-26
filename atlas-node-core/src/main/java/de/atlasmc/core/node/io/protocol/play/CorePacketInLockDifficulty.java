package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInLockDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInLockDifficulty implements PacketCodec<PacketInLockDifficulty> {
	
	@Override
	public void deserialize(PacketInLockDifficulty packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.locked = in.readBoolean();
	}

	@Override
	public void serialize(PacketInLockDifficulty packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeBoolean(packet.locked);
	}
	
	@Override
	public PacketInLockDifficulty createPacketData() {
		return new PacketInLockDifficulty();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInLockDifficulty.class);
	}

}
