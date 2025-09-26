package de.atlasmc.core.node.io.protocol;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.node.io.protocol.status.PacketInPing;
import de.atlasmc.node.io.protocol.status.PacketOutPong;
import de.atlasmc.node.io.protocol.status.PacketOutResponse;

public class CorePacketListenerStatus implements PacketListener {

	private final ConnectionHandler handler;
	
	public CorePacketListenerStatus(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void handlePacket(Packet packet) {
		if (packet.getID() == 0) {
			PacketOutResponse response = new PacketOutResponse();
			NetworkInfo info = AtlasNetwork.getNetworkInfo();
			int version = handler.getProtocol().getVersion();
			response.response = info.getStatusInfo(version);
			handler.sendPacket(response);
		} else if (packet.getID() == 1) {
			PacketInPing ping = (PacketInPing) packet;
			PacketOutPong pong = new PacketOutPong();
			pong.pong = ping.ping;
			handler.sendPacket(pong);
			handler.close();
		}
	}

	@Override
	public void handleUnregister() {}

	@Override
	public void handleSyncPackets(Log logger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasSyncPackets() {
		return false;
	}

}
