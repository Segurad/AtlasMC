package de.atlascore.io.protocol;

import de.atlasmc.io.protocol.ProtocolAdapter;

public class CoreProtocolAdapter implements ProtocolAdapter {

	public static final int VERSION = 754;
	public static final String VERSION_STRING = "[AtlasMC]";
	private final CoreProtocolStatus status;
	private final CoreProtocolLogin login;
	private final CoreProtocolPlay play;
		
	public CoreProtocolAdapter() {
		status = new CoreProtocolStatus();
		login = new CoreProtocolLogin();
		play = new CoreProtocolPlay();
	}
	
	@Override
	public int getVersion() {
		return VERSION;
	}

	@Override
	public String getVersionString() {
		return "v1.16.5";
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

}
