package de.atlascore.io.protocol;

import java.util.UUID;

import de.atlascore.io.protocol.login.CorePacketInLoginStart;
import de.atlascore.io.protocol.login.CorePacketOutLoginSuccess;
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
		System.out.println("PacketLogin: " + id); // TODO delete
		if (id == 0) {
			CorePacketInLoginStart pk = (CorePacketInLoginStart) packet;
			System.out.println("LoginStartAs: " + pk.getName()); // TODO delete
			handler.sendPacket(new CorePacketOutLoginSuccess(new UUID(0, 0), "Segurad"));
		}
	}

	@Override
	public void handleUnregister() {}

}
