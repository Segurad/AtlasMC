package de.atlasmc.core.node.io.protocol;

import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.server.NodeServer;

public class CoreNodePlayer implements NodePlayer {

	private final PlayerConnection con;
	private volatile Player player;
	private final AtlasPlayer atlasPlayer;
	
	public CoreNodePlayer(PlayerConnection con, AtlasPlayer atlasPlayer) {
		this.con = con;
		this.atlasPlayer = atlasPlayer;
	}
	
	@Override
	public void sendTranslation(String key, Object... values) {
		con.sendTranslation(key, values);
	}

	@Override
	public void sendMessage(Chat chat) {
		con.sendMessage(chat);
	}

	@Override
	public void sendMessage(String message) {
		con.sendMessage(message);
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		con.sendMessage(message, type, source, target);
	}

	@Override
	public String getInternalName() {
		return atlasPlayer.getInternalName();
	}

	@Override
	public UUID getInternalUUID() {
		return atlasPlayer.getInternalUUID();
	}

	@Override
	public String getMojangName() {
		return atlasPlayer.getMojangName();
	}

	@Override
	public UUID getMojangUUID() {
		return atlasPlayer.getMojangUUID();
	}

	@Override
	public PlayerConnection getConnection() {
		return con;
	}

	@Override
	public Player getPlayer() {
		return player;
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
