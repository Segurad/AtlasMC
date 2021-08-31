package de.atlascore.io.protocol;

import de.atlascore.io.protocol.status.CorePacketOutPong;
import de.atlascore.io.protocol.status.CorePacketOutResponse;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.status.PacketInPing;

public class CorePacketListenerStatus implements PacketListener {

	private final ConnectionHandler handler;
	
	public CorePacketListenerStatus(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void handlePacket(Packet packet) {
		System.out.println("PacketStatus: " + packet.getID()); // TODO  delete
		if (packet.getID() == 0) {
			handler.sendPacket(new CorePacketOutResponse(handler.getProxy().createServerListResponse(CoreProtocolAdapter.VERSION)));
		} else if (packet.getID() == 1) {
			PacketInPing ping = (PacketInPing) packet;
			handler.sendPacket(new CorePacketOutPong(ping.getPing()));
			handler.close();
		}
	}

	@Override
	public void handleUnregister() {}

}
