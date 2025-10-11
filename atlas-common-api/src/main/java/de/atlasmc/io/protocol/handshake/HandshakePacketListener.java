package de.atlasmc.io.protocol.handshake;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.log.Log;

public class HandshakePacketListener implements PacketListener {
		
	private final HandshakeProtocol protocol;
	private final ConnectionHandler handler;
	
	public HandshakePacketListener(ConnectionHandler handler, HandshakeProtocol protocol) {
		this.handler = handler;
		this.protocol = protocol;
	}

	@Override
	public void handlePacket(Packet packet) {
		@SuppressWarnings("unchecked")
		HandshakePacketIO<Packet> handler = (HandshakePacketIO<Packet>) protocol.getPacketIO(packet.getID());
		if (handler == null)
			throw new IllegalStateException("No handler for handshake with id (" + packet.getID() + ") found!");
		handler.handle(this.handler, packet);
	}

	@Override
	public void handleUnregister() {
		// not required
	}

	@Override
	public void handleSyncPackets(Log logger) {
		// not required
	}

	@Override
	public boolean hasSyncPackets() {
		return false;
	}

}
