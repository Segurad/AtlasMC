package de.atlasmc.io.protocol.play;

import de.atlasmc.Gamemode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.world.Dimension;
import de.atlasmc.world.DimensionCodec;

@DefaultPacketID(PacketPlay.OUT_JOIN_GAME)
public class PacketOutJoinGame extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private boolean hardcore;
	private Gamemode gamemode, oldGamemode;
	private String[] worlds;
	private DimensionCodec dimensionCodec;
	private Dimension dimension;
	private String world;
	private long seed;
	private int viewDistance;
	private boolean reducedDebugInfo = true;
	private boolean respawnScreen = true;
	private boolean debug;
	private boolean flat;
	
	public int getEntityID() {
		return entityID;
	}

	public boolean isHardcore() {
		return hardcore;
	}

	public Gamemode getGamemode() {
		return gamemode;
	}

	public Gamemode getOldGamemode() {
		return oldGamemode;
	}

	public String[] getWorlds() {
		return worlds;
	}

	public DimensionCodec getDimensionCodec() {
		return dimensionCodec;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public String getWorld() {
		return world;
	}

	public long getSeed() {
		return seed;
	}

	public int getViewDistance() {
		return viewDistance;
	}

	public boolean isReducedDebugInfo() {
		return reducedDebugInfo;
	}

	public boolean isRespawnScreen() {
		return respawnScreen;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isFlat() {
		return flat;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setHardcore(boolean hardcore) {
		this.hardcore = hardcore;
	}

	public void setGamemode(Gamemode gamemode) {
		this.gamemode = gamemode;
	}

	public void setOldGamemode(Gamemode oldGamemode) {
		this.oldGamemode = oldGamemode;
	}

	public void setWorlds(String[] worlds) {
		this.worlds = worlds;
	}

	public void setDimensionCodec(DimensionCodec dimensionCodec) {
		this.dimensionCodec = dimensionCodec;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public void setViewDistance(int viewDistance) {
		this.viewDistance = viewDistance;
	}

	public void setReducedDebugInfo(boolean reducedDebugInfo) {
		this.reducedDebugInfo = reducedDebugInfo;
	}

	public void setRespawnScreen(boolean respawnScreen) {
		this.respawnScreen = respawnScreen;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	@Override
	public int getDefaultID() {
		return OUT_JOIN_GAME;
	}

}
