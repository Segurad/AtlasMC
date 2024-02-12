package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.Gamemode;
import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_LOGIN)
public class PacketOutLogin extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public boolean hardcore;
	public List<NamespacedKey> worlds;
	public int maxPlayers;
	public int viewDistance;
	public int simulationDistance;
	public boolean reducedDebugInfo = true;
	public boolean respawnScreen = true;
	public boolean limitedCrafting;
	public NamespacedKey dimension;
	public NamespacedKey world;
	public long seed;
	public Gamemode gamemode;
	public Gamemode oldGamemode;
	public boolean debug;
	public boolean flat;
	public NamespacedKey deathWorld;
	public long deathPosition;
	public int portalCooldown;

	@Override
	public int getDefaultID() {
		return OUT_LOGIN;
	}

}
