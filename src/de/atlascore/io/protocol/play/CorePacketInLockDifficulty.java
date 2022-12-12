package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInLockDifficulty extends CoreAbstractHandler<PacketInLockDifficulty> {
	
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

}
