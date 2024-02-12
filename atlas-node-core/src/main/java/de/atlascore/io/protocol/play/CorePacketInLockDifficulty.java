package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInLockDifficulty implements PacketIO<PacketInLockDifficulty> {
	
	@Override
	public void read(PacketInLockDifficulty packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setLocked(in.readBoolean());
	}

	@Override
	public void write(PacketInLockDifficulty packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeBoolean(packet.isLocked());
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
