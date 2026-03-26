package de.atlasmc.node.io.protocol;

import java.util.Objects;

import de.atlasmc.node.io.protocol.ProtocolAdapter;
import de.atlasmc.util.annotation.NotNull;

public class ProtocolAdapter {

	/**
	 * The protocol version id used by the latest supported protocol
	 */
	public static final int VERSION = 773;
	/**
	 * The protocol version used by the latest supported protocol
	 */
	public static final String VERSION_STRING = "v1.21.10";
	
	private final int version;
	private final String verionString;
	private final ProtocolStatus status;
	private final ProtocolLogin login;
	private final ProtocolPlay play;
	private final ProtocolConfiguration config;
		
	public ProtocolAdapter(ProtocolStatus status, ProtocolLogin login, ProtocolPlay play, ProtocolConfiguration config) {
		this(VERSION, VERSION_STRING, status, login, play, config);
	}
	
	public ProtocolAdapter(int version, String versionString, ProtocolStatus status, ProtocolLogin login, ProtocolPlay play, ProtocolConfiguration config) {
		this.version = version;
		this.verionString = Objects.requireNonNull(versionString);
		this.status = Objects.requireNonNull(status);
		this.login = Objects.requireNonNull(login);
		this.play = Objects.requireNonNull(play);
		this.config = Objects.requireNonNull(config);
	}
	
	public int getVersion() {
		return version;
	}

	@NotNull
	public String getVersionString() {
		return verionString;
	}

	@NotNull
	public ProtocolStatus getStatusProtocol() {
		return status;
	}

	@NotNull
	public ProtocolLogin getLoginProtocol() {
		return login;
	}

	@NotNull
	public ProtocolPlay getPlayProtocol() {
		return play;
	}
	
	@NotNull
	public ProtocolConfiguration getConfigurationProtocol() {
		return config;
	}

}
