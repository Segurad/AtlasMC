package de.atlasmc.core.node.event.socket;

import de.atlasmc.chat.ChatUtil;
import de.atlasmc.core.node.io.protocol.CoreLoginHandler;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.event.ListenerAutoregister;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.network.player.PlayerConnectionConfig;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.event.socket.AsyncPlayerHandshakeEvent;
import de.atlasmc.node.event.socket.AsyncPlayerLoginAttemptEvent;
import de.atlasmc.node.io.protocol.ProtocolAdapter;
import de.atlasmc.node.io.protocol.handshake.HandshakeData;

@ListenerAutoregister(isDefault = true)
public class CoreSocketListener implements Listener {
	
	@EventHandler
	public void onHandShake(AsyncPlayerHandshakeEvent event) {
		if (event.isCancelled()) {
			event.getConnection().close();
		}
		var data = event.getData();
		var con = event.getConnection();
		var protManager = AtlasNode.getProtocolAdapterManager();
		ProtocolAdapter adapter = protManager.getProtocol(data.version());
		if (adapter == null) {
			con.getLogger().debug("No Protocol with found with version: {}", data.version());
			adapter = protManager.getDefaultProtocol();
		}
		final Protocol prot;
		final Object context;
		switch (data.intent()) {
		case HandshakeData.INTENT_LOGIN:
		case HandshakeData.INTENT_TRANSFER: {
			prot = adapter.getLoginProtocol();
			context = new CoreLoginHandler((ServerSocketConnectionHandler) con, data);
			break;
		}
		case HandshakeData.INTENT_STATUS:
			prot = adapter.getStatusProtocol();
			context = data;
			break;
		default:
			con.getLogger().error("Invalid intent {} for connection {}", data.intent(), con);
			con.close();
			return;
		};
		con.setProtocol(prot);
		con.setSyncPacketHandling(true);
		con.getInboundListeners().addFirst("default", prot.createDefaultPacketListenerServerbound(context));
	}
	
	@EventHandler
	public void onLoginAttempt(AsyncPlayerLoginAttemptEvent event) {
		if (event.isCancelled()) {
			event.getConnection().disconnect(ChatUtil.toChat("Rejected login"));
			return;
		}
		var con = event.getConnection();
		var conCfg = con.getSocket().getConfig();
		PlayerConnectionConfig cfg = conCfg.getConnectionConfig("player-connection");
	}

}
