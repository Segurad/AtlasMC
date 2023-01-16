package de.atlascore.io.protocol;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import de.atlasmc.io.protocol.login.PacketInLoginStart;
import de.atlasmc.io.protocol.login.PacketOutLoginSuccess;
import de.atlasmc.io.protocol.login.PacketInEncryptionResponse;
import de.atlascore.io.ConnectionHandler;
import de.atlasmc.Atlas;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.protocol.ProtocolAdapter;

public class CorePacketListenerLogin implements PacketListener {

	private static final List<PacketHandler> HANDLERS;

	static {
		HANDLERS = List.of(
			(listener, packet) -> {
				PacketInLoginStart packetIn = (PacketInLoginStart) packet;
				System.out.println("LoginStartAs: " + packetIn.getName()); // TODO delete
				final ConnectionHandler handler = listener.handler;
				PacketOutLoginSuccess packetOut = new PacketOutLoginSuccess();
				packetOut.setUUID(new UUID(0, 0));
				packetOut.setUsername(new String("Segurad"));
				handler.sendPacket(packetOut);
				int version = handler.getProtocol().getVersion();
				ProtocolAdapter adapter = Atlas.getProtocolAdapter(version);
				Protocol play = adapter.getPlayProtocol();
				handler.setProtocol(play, play.createDefaultPacketListener(handler));
			},
			(listener, packet) -> {
				PacketInEncryptionResponse packetIn = (PacketInEncryptionResponse) packet;
			}
		);
	}

	private final ConnectionHandler handler;

	public CorePacketListenerLogin(ConnectionHandler handler) {
		this.handler = handler;
	}

	@Override
	public void handlePacket(Packet packet) throws IOException {
		final int id = packet.getID();
		if (id < 0 || id > 2)
			throw new IOException("Invalid packet id on login: " + id);
		PacketHandler consumer = HANDLERS.get(id);
		consumer.handle(this, packet);
	}

	@Override
	public void handleUnregister() {}
	
	@FunctionalInterface
	private interface PacketHandler {
		public void handle(CorePacketListenerLogin listener, Packet packet);
	}

	@Override
	public void handleSyncPackets() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
