package de.atlasmc.io.protocol;

public interface ProtocolAdapter {

	public int getVersion();
	
	public String getVersionString();
	
	public ProtocolStatus getStatusProtocol();
	
	public ProtocolLogin getLoginProtocol();
	
	public ProtocolPlay getPlayProtocol();
	
	public ProtocolConfiguration getConfigurationProtocol();
	
}
