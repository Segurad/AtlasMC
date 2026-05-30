package de.atlasmc.core.node.event.socket;

import de.atlasmc.chat.ChatUtil;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.network.player.PlayerConnectionConfig;
import de.atlasmc.node.event.socket.AsyncPlayerLoginAttemptEvent;

public class CoreLoginListener implements Listener {
	
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
