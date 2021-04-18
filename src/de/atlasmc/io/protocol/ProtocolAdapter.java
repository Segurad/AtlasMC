package de.atlasmc.io.protocol;

import de.atlasmc.io.Protocol;

public interface ProtocolAdapter {

	public int getVersion();
	public String getVersionString();
	public Protocol getStatusProtocol();
	public Protocol getLoginProtocol();
	public Protocol getPlayProtocol();
	
}
