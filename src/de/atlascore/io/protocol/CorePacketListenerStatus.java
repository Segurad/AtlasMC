package de.atlascore.io.protocol;

import java.io.IOException;

import de.atlascore.io.ProxyConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.status.PacketInPing;
import de.atlasmc.io.protocol.status.PacketOutPong;
import de.atlasmc.io.protocol.status.PacketOutResponse;

public class CorePacketListenerStatus implements PacketListener {

	private final ProxyConnectionHandler handler;
	
	public CorePacketListenerStatus(ProxyConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void handlePacket(Packet packet) {
		System.out.println("PacketStatus: " + packet.getID()); // TODO  delete
		if (packet.getID() == 0) {
			PacketOutResponse response = new PacketOutResponse();
			response.setResponse(handler.getProxy().createServerListResponse(CoreProtocolAdapter.VERSION).toString());
			handler.sendPacket(response);
		} else if (packet.getID() == 1) {
			PacketInPing ping = (PacketInPing) packet;
			PacketOutPong pong = new PacketOutPong();
			pong.setPong(ping.getPing());
			handler.sendPacket(pong);
			handler.close();
		}
	}

	@Override
	public void handleUnregister() {}

	@Override
	public void handleSyncPackets() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
