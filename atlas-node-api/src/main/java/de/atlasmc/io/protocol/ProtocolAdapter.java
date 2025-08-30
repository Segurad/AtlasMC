package de.atlasmc.io.protocol;

import de.atlasmc.util.annotation.NotNull;

public interface ProtocolAdapter {

	int getVersion();
	
	@NotNull
	String getVersionString();
	
	@NotNull
	ProtocolStatus getStatusProtocol();
	
	@NotNull
	ProtocolLogin getLoginProtocol();
	
	@NotNull
	ProtocolPlay getPlayProtocol();
	
	@NotNull
	ProtocolConfiguration getConfigurationProtocol();
	
}
