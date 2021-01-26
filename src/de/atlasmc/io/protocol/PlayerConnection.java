package de.atlasmc.io.protocol;

import de.atlasmc.inventory.Inventory;
import de.atlasmc.io.Packet;
import de.atlasmc.io.atlasnetwork.Proxy;
import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;
import de.atlasmc.io.atlasnetwork.server.Server;

public interface PlayerConnection {

	/**
	 * 
	 * @param pack
	 * @throws IllegalArgumentException if Protocol-Version and Packet-Version are not equal
	 */
	public void send(Packet pack);
	
	public boolean canRead();

	public ProtocolAdapter getProtocol();

	public Packet read();
	
	public void close();

	public void updateWindowSlots(Inventory abstractCoreInventory, int... slots);
	
	public Proxy getMainProxy();
	public Proxy getProxy();
	public Server getServer();
	
	public AtlasPlayer getPlayer();
	
	public PlayerConnectionState getState();
	
	public void setState(PlayerConnectionState state);

}
