package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUpdateViewDistance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateViewDistance extends AbstractPacket implements PacketOutUpdateViewDistance {

	private int distance;
	
	public CorePacketOutUpdateViewDistance() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutUpdateViewDistance(int distance) {
		this();
		this.distance = distance;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		distance = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(distance, out);
	}

	@Override
	public int getDistance() {
		return distance;
	}

}
