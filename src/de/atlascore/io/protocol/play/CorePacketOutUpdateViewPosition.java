package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUpdateViewPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateViewPosition extends AbstractPacket implements PacketOutUpdateViewPosition {

	private int chunkX, chunkZ;
	
	public CorePacketOutUpdateViewPosition() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutUpdateViewPosition(int chunkX, int chunkZ) {
		this();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		chunkX = readVarInt(in);
		chunkZ = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(chunkX, out);
		writeVarInt(chunkZ, out);
	}

	@Override
	public int getChunkX() {
		return chunkX;
	}

	@Override
	public int getChunkZ() {
		return chunkZ;
	}

}
