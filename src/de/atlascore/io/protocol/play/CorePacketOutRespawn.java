package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Gamemode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutRespawn;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.Dimension;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRespawn extends AbstractPacket implements PacketOutRespawn {

	private Dimension dimension;
	private String world;
	private long seed;
	private Gamemode gamemode, previous;
	private boolean debug, flat, copymeta;
	
	public CorePacketOutRespawn() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutRespawn(Dimension dimension, String world, long seed, Gamemode gamemode, Gamemode previous, boolean debug, boolean flat, boolean copymeta) {
		this();
		this.dimension = dimension;
		this.world = world;
		this.seed = seed;
		this.gamemode = gamemode;
		this.previous = previous;
		this.debug = debug;
		this.flat = flat;
		this.copymeta = copymeta;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		// TODO
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		dimension.toNBT(new NBTNIOWriter(out), false);
		writeString(world, out);
		out.writeLong(seed);
		out.writeByte(gamemode.ordinal());
		out.writeByte(previous.ordinal());
		out.writeBoolean(debug);
		out.writeBoolean(flat);
		out.writeBoolean(copymeta);
	}

}
