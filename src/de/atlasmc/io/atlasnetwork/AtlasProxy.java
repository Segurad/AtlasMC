package de.atlasmc.io.atlasnetwork;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;
import de.atlasmc.util.Validate;

/**
 * Proxy in the AtlasMC network
 * may or may not be part of this AtlasNode
 * @author Segurad
 *
 */
public class AtlasProxy implements Proxy {

	private final AtlasNode node;
	private final InetSocketAddress address;
	private List<AtlasPlayer> connections;
	private Mode mode;
	
	public AtlasProxy(AtlasNode node, int port) {
		this.node = node;
		this.address = new InetSocketAddress(port);
		this.connections = new ArrayList<AtlasPlayer>();
		this.mode = Mode.ALLOW_ALL;
	}
	
	@Override
	public AtlasNode getNode() {
		return node;
	}

	@Override
	public InetSocketAddress getAddress() {
		return address;
	}

	@Override
	public List<AtlasPlayer> getConnections() {
		return connections;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		Validate.notNull(mode, "Mode can not be null!");
		this.mode = mode;
	}

}
