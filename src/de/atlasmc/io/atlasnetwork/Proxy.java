package de.atlasmc.io.atlasnetwork;

import java.net.InetSocketAddress;
import java.util.List;

import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;

public interface Proxy {
	
	public AtlasNode getNode();
	public InetSocketAddress getAddress();
	public List<AtlasPlayer> getConnections();
	public Mode getMode();
	public void setMode(Mode mode);
	
	/**
	 * The Mode defines how the Proxy will new connections
	 * @author Segurad
	 *
	 */
	public static enum Mode {
		ALLOW_ALL,
		ALLOW_EXTERNAL,
		ALLOW_INTERNAL,
		SERVERLIST_PING_ONLY
	}

}
