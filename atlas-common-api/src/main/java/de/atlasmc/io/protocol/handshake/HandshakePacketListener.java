package de.atlasmc.io.protocol.handshake;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.PacketListener;

public class HandshakePacketListener implements PacketListener {
		
	private final HandshakeProtocol protocol;
	
	public HandshakePacketListener(ConnectionHandler handler, HandshakeProtocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public void handlePacket(ConnectionHandler handler, Packet packet) {
		@SuppressWarnings("unchecked")
		HandshakePacketCodec<Packet> codec = (HandshakePacketCodec<Packet>) protocol.getPacketIO(packet.getID());
		if (codec == null)
			throw new IllegalStateException("No handler for handshake with id (" + packet.getID() + ") found!");
		codec.handle(handler, packet);
	}

	@Override
	public void handlePacketSync(ConnectionHandler handler, Packet packet) throws IOException {
		// not required
	}

}
