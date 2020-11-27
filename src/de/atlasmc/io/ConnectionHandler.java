package de.atlasmc.io;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;

public interface ConnectionHandler {

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
	
	public Player getPlayer();

}
