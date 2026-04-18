package de.atlasmc.core.node.io.protocol;

import de.atlasmc.io.Packet;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.PacketListener;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.node.io.protocol.status.ServerboundPing;
import de.atlasmc.node.io.protocol.status.ClientboundResponse;
import de.atlasmc.node.io.protocol.status.ClientboundPong;

public class CorePacketListenerStatus implements PacketListener {
	
	public static final CorePacketListenerStatus INSTANCE = new CorePacketListenerStatus();
	
	@Override
	public void handlePacket(ConnectionHandler handler, Packet packet) {
		if (packet.getID() == 0) {
			ClientboundResponse response = new ClientboundResponse();
			NetworkInfo info = AtlasNetwork.getNetworkInfo();
			int version = handler.getProtocol().getVersion();
			response.response = info.getStatusInfo(version);
			handler.sendPacket(response);
			packet.setHandled(true);
		} else if (packet.getID() == 1) {
			ServerboundPing ping = (ServerboundPing) packet;
			ClientboundPong pong = new ClientboundPong();
			pong.pong = ping.ping;
			handler.sendPacket(pong);
			handler.close();
			packet.setHandled(true);
		}
	}

}
