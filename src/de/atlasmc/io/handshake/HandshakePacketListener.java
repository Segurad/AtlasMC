package de.atlasmc.io.handshake;

import de.atlasmc.Atlas;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.protocol.ProtocolAdapter;

public class HandshakePacketListener implements PacketListener {

	private final ConnectionHandler handler;
	
	public HandshakePacketListener(ConnectionHandler handler) {
		this.handler = handler;
	}

	@Override
	public void handlePacket(Packet packet) {
		switch (packet.getID()) {
		case 0x00:
			PacketMinecraftHandshake pk = (PacketMinecraftHandshake) packet;
			ProtocolAdapter adapter = Atlas.getProtocolAdapter(pk.getProtocolVersion());
			if (adapter == null) {
				handler.close();
			}
			final int nextState = pk.getNextState();
			if (nextState == 1) {
				final Protocol prot = adapter.getStatusProtocol();
				handler.setProtocol(prot, prot.createDefaultPacketListener(handler));
			} else if (nextState == 2) {
				final Protocol prot = adapter.getLoginProtocol();
				handler.setProtocol(prot, prot.createDefaultPacketListener(handler));
			}
		}
	}

	@Override
	public void handleUnregister() {}

}
