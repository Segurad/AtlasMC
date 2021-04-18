package de.atlascore.io.protocol;

import de.atlasmc.io.protocol.ProtocolAdapter;

public class CoreProtocolAdapter implements ProtocolAdapter {

	public static final int VERSION = 754;
	public static final String VERSION_STRING = "[AtlasMC]";
	private final CoreStatusProtocol status;
	private final CoreLoginProtocol login;
	private final CorePlayProtocol play;
		
	public CoreProtocolAdapter() {
		status = new CoreStatusProtocol();
		login = new CoreLoginProtocol();
		play = new CorePlayProtocol();
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
	public CoreStatusProtocol getStatusProtocol() {
		return status;
	}

	@Override
	public CoreLoginProtocol getLoginProtocol() {
		return login;
	}

	@Override
	public CorePlayProtocol getPlayProtocol() {
		return play;
	}

}
