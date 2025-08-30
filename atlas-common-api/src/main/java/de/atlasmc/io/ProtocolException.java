package de.atlasmc.io;

public class ProtocolException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Packet packet;
	private final Protocol protocol;
	
	public ProtocolException(String message) {
		this(message, null, null);
	}
	
	public ProtocolException(String message, Protocol protocol) {
		this(message, null, protocol, null);
	}
	
	public ProtocolException(String message, Protocol protocol, Packet packet) {
		this(message, null, protocol, packet);
	}
	
	public ProtocolException(String message, Throwable cause) {
		this(message, cause, null, null);
	}
	
	public ProtocolException(String message, Throwable cause, Protocol protocol, Packet packet) {
		super(message, cause);
		this.packet = packet;
		this.protocol = protocol;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
	public Protocol getProtocol() {
		return protocol;
	}
	
}
