package de.atlasmc.core.node.io.protocol;

import java.util.Objects;

import de.atlasmc.chat.Chat;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.server.NodeServer;

public class CoreNodePlayer implements NodePlayer {

	private final PlayerConnection con;
	private final AtlasPlayer atlasPlayer;
	
	public CoreNodePlayer(PlayerConnection con, AtlasPlayer atlasPlayer) {
		this.con = Objects.requireNonNull(con);
		this.atlasPlayer = Objects.requireNonNull(atlasPlayer);
	}

	@Override
	public void sendMessage(Chat chat, boolean overlay) {
		con.sendMessage(chat, overlay);
	}

	@Override
	public PlayerConnection getConnection() {
		return con;
	}

	@Override
	public Player getPlayer() {
		return con.getPlayer();
	}
	
	@Override
	public boolean hasPlayer() {
		return con.hasPlayer();
	}

	@Override
	public AtlasPlayer getAtlasPlayer() {
		return atlasPlayer;
	}

	@Override
	public NodeSocket getProxy() {
		return con.getSocket();
	}

	@Override
	public NodeServer getLocalServer() {
		// TODO Auto-generated method stub
		return null;
	}

}
