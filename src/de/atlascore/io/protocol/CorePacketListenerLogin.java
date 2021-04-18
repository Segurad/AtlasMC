package de.atlascore.io.protocol;

import de.atlascore.io.protocol.login.CorePacketOutDisconnect;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;

public class CorePacketListenerLogin implements PacketListener {

	private final ConnectionHandler handler;
	
	public CorePacketListenerLogin(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void handlePacket(Packet packet) {
		final int id = packet.getID();
		if (id == 0) {
			handler.sendPacket(new CorePacketOutDisconnect("Developement"));
		}
	}

	@Override
	public void handleUnregister() {}

}
