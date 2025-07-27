package de.atlascore.io.protocol;

import java.util.UUID;

import de.atlasmc.NodePlayer;
import de.atlasmc.atlasnetwork.player.AtlasPlayer;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.server.NodeServer;

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
	public LocalProxy getProxy() {
		return con.getProxy();
	}

	@Override
	public NodeServer getLocalServer() {
		// TODO Auto-generated method stub
		return null;
	}

}
