package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUpdateLight;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateLight extends AbstractPacket implements PacketOutUpdateLight {

	private int chunkX, chunkZ, skyMask, blockMask, emptySkyMask, emptyBlockMask;
	private boolean trustEdges;
	
	public CorePacketOutUpdateLight() {
		super(CoreProtocolAdapter.VERSION);
	}

	public CorePacketOutUpdateLight(int chunkX, int chunkZ, boolean trustEdges, int skyMask, int blockMask, int emptySkyMask, int emptyBlockMask) {
		this();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.trustEdges = trustEdges;
		this.skyMask = skyMask;
		this.blockMask = blockMask;
		this.emptySkyMask = emptySkyMask;
		this.emptyBlockMask = emptyBlockMask;
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		chunkX = readVarInt(in);
		chunkZ = readVarInt(in);
		trustEdges = in.readBoolean();
		skyMask = readVarInt(in);
		blockMask = readVarInt(in);
		emptySkyMask = readVarInt(in);
		emptyBlockMask = readVarInt(in);
		in.skipBytes(in.readableBytes()); // TODO
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(chunkX, out);
		writeVarInt(chunkZ, out);
		out.writeBoolean(trustEdges);
		writeVarInt(0, out);
		writeVarInt(0, out);
		writeVarInt(0, out);
		writeVarInt(0, out);
		// TODO
		/*writeVarInt(skyMask, out);
		writeVarInt(blockMask, out);
		writeVarInt(emptySkyMask, out);
		writeVarInt(emptyBlockMask, out);*/
	}

}
