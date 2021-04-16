package de.atlasmc.io.protocol;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.AtlasPlayer;
import de.atlasmc.io.Packet;

public interface PlayerConnection {

	/**
	 * 
	 * @param pack
	 * @throws IllegalArgumentException if Protocol-Version and Packet-Version are not equal
	 */
	public void send(Packet pack);

	public ProtocolAdapter getProtocol();
	
	public void close();
	
	public Proxy getMainProxy();
	public Proxy getProxy();
	
	public AtlasPlayer getPlayer();
	
	public PlayerConnectionState getState();
	public void setState(PlayerConnectionState state);

}
