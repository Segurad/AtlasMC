package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Gamemode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutJoinGame;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.Dimension;
import de.atlasmc.world.DimensionCodec;
import io.netty.buffer.ByteBuf;

public class CorePacketOutJoinGame extends AbstractPacket implements PacketOutJoinGame {

	private int id;
	private boolean hardcore;
	private Gamemode gamemode, oldGamemode = null;
	private String[] worlds;
	private DimensionCodec codec;
	private Dimension dimension;
	private String world;
	private long seed;
	private int viewDistance;
	private boolean reducedDebugInfo = true;
	private boolean respawnScreen = true;
	private boolean debug = false;
	private boolean flat = false;
	
	public CorePacketOutJoinGame() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutJoinGame(int id, boolean hardcore, Gamemode gamemode, String[] worlds, DimensionCodec codec, Dimension dim, String world, long seed, int viewDistance) {
		this();
		this.id = id;
		this.hardcore = hardcore;
		this.gamemode = gamemode;
		this.worlds = worlds;
		this.codec = codec;
		this.dimension = dim;
		this.world = world;
		this.seed = seed;
		this.viewDistance = viewDistance;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(id);
		out.writeBoolean(hardcore);
		out.writeByte(gamemode.ordinal());
		out.writeByte(oldGamemode != null ? oldGamemode.ordinal() : -1);
		writeVarInt(worlds.length, out);
		for (String s : worlds) {
			writeString(s, out);
		}
		NBTNIOWriter writer = new NBTNIOWriter(out);
		codec.toNBT(writer, false);
		dimension.toNBT(writer, false);
		writeString(world, out);
		out.writeLong(seed);
		writeVarInt(0, out);
		writeVarInt(viewDistance, out);
		out.writeBoolean(reducedDebugInfo);
		out.writeBoolean(respawnScreen);
		out.writeBoolean(debug);
		out.writeBoolean(flat);
	}

	@Override
	public void setOldGamemode(Gamemode gamemode) {
		this.oldGamemode = gamemode;
	}

	@Override
	public void setReducedDebugInfo(boolean value) {
		this.reducedDebugInfo = value;
	}

	@Override
	public void setEnableRespawnScreen(boolean value) {
		this.respawnScreen = value;
	}

	@Override
	public void setDebug(boolean value) {
		this.debug = value;
	}

	@Override
	public void setFlat(boolean flat) {
		this.flat = flat;
	}

}
