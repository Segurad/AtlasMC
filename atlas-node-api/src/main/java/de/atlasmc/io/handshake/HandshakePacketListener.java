package de.atlasmc.io.handshake;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.log.Log;

public class HandshakePacketListener implements PacketListener {
		
	private final HandshakeProtocol protocol;
	private final ProxyConnectionHandler handler;
	
	public HandshakePacketListener(ProxyConnectionHandler handler, HandshakeProtocol protocol) {
		this.handler = handler;
		this.protocol = protocol;
	}

	@Override
	public void handlePacket(Packet packet) {
		handler.getProxy().getLogger().debug("[{}] Handshaking ID: {}", handler.getRemoteAddress(), packet.getID());
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
