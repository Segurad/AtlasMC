package de.atlasmc.node.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.Gamemode;

@DefaultPacketID(packetID = PacketPlay.OUT_RESPAWN, definition = "respawn")
public class PacketOutRespawn extends AbstractPacket implements PacketPlayOut {
	
	public int dimension;
	public NamespacedKey world;
	public long seed;
	public Gamemode gamemode;
	public Gamemode previous;
	public boolean debug;
	public boolean flat;
	public NamespacedKey deathDimension;
	public long deathLocation;
	public int portalCooldown;
	/**
	 * <ul>
	 * <li>0x01 = keep attributes</li>
	 * <li>0x02 = keep metadata</li>
	 * </ul>
	 */
	public int dataKept;

	@Override
	public int getDefaultID() {
		return OUT_RESPAWN;
	}

}
