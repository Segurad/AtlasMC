package de.atlasmc.node.io.handshake;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
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
		handler.getLogger().debug("[{}] Handshaking ID: {}", handler.getRemoteAddress(), packet.getID());
		@SuppressWarnings("unchecked")
		HandshakePaketIO<Packet> handler = (HandshakePaketIO<Packet>) protocol.getPacketIO(packet.getID());
		if (handler == null)
			throw new IllegalStateException("No handler for handshake with id (" + packet.getID() + ") found!");
		handler.handle(this.handler, packet);
	}

	@Override
	public void handleUnregister() {}

	@Override
	public void handleSyncPackets(Log logger) {}

	@Override
	public boolean hasSyncPackets() {
		return false;
	}

}
