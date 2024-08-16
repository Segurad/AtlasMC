package de.atlascore.io.protocol;

import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.io.protocol.status.PacketInPing;
import de.atlasmc.io.protocol.status.PacketOutPong;
import de.atlasmc.io.protocol.status.PacketOutResponse;
import de.atlasmc.log.Log;

public class CorePacketListenerStatus implements PacketListener {

	private final ProxyConnectionHandler handler;
	
	public CorePacketListenerStatus(ProxyConnectionHandler handler) {
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
