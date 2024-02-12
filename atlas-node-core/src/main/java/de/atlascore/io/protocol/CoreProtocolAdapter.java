package de.atlascore.io.protocol;

import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolConfiguration;

public class CoreProtocolAdapter implements ProtocolAdapter {

	public static final int VERSION = 764;
	public static final String VERSION_STRING = "[AtlasMC]";
	private final CoreProtocolStatus status;
	private final CoreProtocolLogin login;
	private final CoreProtocolPlay play;
	private final CoreProtocolConfiguration config;
		
	public CoreProtocolAdapter() {
		status = new CoreProtocolStatus();
		login = new CoreProtocolLogin();
		play = new CoreProtocolPlay();
		config = new CoreProtocolConfiguration();
	}
	
	@Override
	public int getVersion() {
		return VERSION;
	}

	@Override
	public String getVersionString() {
		return "v1.20.2";
	}

	@Override
	public CoreProtocolStatus getStatusProtocol() {
		return status;
	}

	@Override
	public CoreProtocolLogin getLoginProtocol() {
		return login;
	}

	@Override
	public CoreProtocolPlay getPlayProtocol() {
		return play;
	}
	
	@Override
	public ProtocolConfiguration getConfigurationProtocol() {
		return config;
	}

}
