package de.atlasmc.io;

import java.io.IOException;

public interface PacketListener {
	
	/**
	 * Handle a inbound {@link Packet}
	 * @param packet
	 * @throws IOException
	 */
	public void handlePacket(Packet packet) throws IOException;
	
	/**
	 * Handle queued packets sync if needed
	 * @throws IOException
	 */
	public void handleSyncPackets() throws IOException;
	
	public void handleUnregister();

}
