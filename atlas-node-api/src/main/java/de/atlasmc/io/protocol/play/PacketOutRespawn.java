package de.atlasmc.io.protocol.play;

import de.atlasmc.Gamemode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.world.Dimension;

@DefaultPacketID(PacketPlay.OUT_RESPAWN)
public class PacketOutRespawn extends AbstractPacket implements PacketPlayOut {
	
	private Dimension dimension;
	private String world;
	private long seed;
	private Gamemode gamemode;
	private Gamemode previous;
	private boolean debug;
	private boolean flat;
	private boolean copymeta;
	
	public Dimension getDimension() {
		return dimension;
	}

	public String getWorld() {
		return world;
	}

	public long getSeed() {
		return seed;
	}

	public Gamemode getGamemode() {
		return gamemode;
	}

	public Gamemode getPreviousGamemode() {
		return previous;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isFlat() {
		return flat;
	}

	public boolean isCopymeta() {
		return copymeta;
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

	public void setGamemode(Gamemode gamemode) {
		this.gamemode = gamemode;
	}

	public void setPrevious(Gamemode previous) {
		this.previous = previous;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public void setCopymeta(boolean copymeta) {
		this.copymeta = copymeta;
	}

	@Override
	public int getDefaultID() {
		return OUT_RESPAWN;
	}

}
