package de.atlascore.master;

import java.net.InetAddress;
import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.Future;

import de.atlasmc.atlasnetwork.player.AtlasPlayer;
import de.atlasmc.atlasnetwork.proxy.AtlasSocket;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHandler;

/**
 * Stores the profile data of a player
 */
public class CoreAtlasPlayer implements AtlasPlayer {
	
	private final CoreProfileManager handler;
	private final String mojangName;
	private final String internalName;
	private final UUID mojangUUID;
	private final UUID internalUUID;
	private final Date firstJoin;
	private volatile Date lastJoin;
	private volatile AtlasSocket originProxy;
	private volatile AtlasSocket proxy;
	private volatile Server currentServer;
	private volatile PermissionHandler permhandler;
	
	public CoreAtlasPlayer(CoreProfileManager handler, String mojangName, UUID mojangUUID, String name, UUID uuid, Date firstJoin, Date lastJoin) {
		this.handler = handler;
		this.mojangName = mojangName;
		this.mojangUUID = mojangUUID;
		this.internalName = name;
		this.internalUUID = uuid;
		this.firstJoin = firstJoin;
		this.lastJoin = lastJoin;
	}

	public String getMojangName() {
		return mojangName;
	}

	public UUID getMojangUUID() {
		return mojangUUID;
	}

	public AtlasSocket getOriginProxy() {
		return originProxy;
	}

	public AtlasSocket getProxy() {
		return proxy;
	}

	public Server getCurrentServer() {
		return currentServer;
	}

	@Override
	public UUID getInternalUUID() {
		return internalUUID;
	}

	@Override
	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		handler.updateInternalName(this, name);
	}

	@Override
	public Future<Boolean> sendToServer(Server server) {
		// TODO implement
		throw new UnsupportedOperationException("Not implemented!");
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return permhandler;
	}

	public void setPermissionHandler(PermissionHandler handler) {
		this.permhandler = handler;
	}

	@Override
	public Permission getPermission(CharSequence permission) {
		return permhandler != null ? permhandler.getPermission(permission) : null;			
	}

	@Override
	public void sendMessage(String message) {
		sendInternalMessage(message);
	}

	@Override
	public void sendMessage(Chat chat) {
		if (chat == null)
			throw new IllegalArgumentException("Chat can not be null!");
		sendInternalMessage(chat.toText());
		// TODO handle chat
	}
	
	@Override
	public void sendTranslation(String key, Object... values) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendInternalMessage(String message) {
		
	}

	@Override
	public Date getFirstJoin() {
		return firstJoin;
	}

	@Override
	public Date getLastJoin() {
		return lastJoin;
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InetAddress getIPAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastJoinDate(Date date) {
		if (date == null)
			throw new IllegalArgumentException("Date can not be null!");
		this.lastJoin = date;	
		handler.updateLastJoind(this, date);
	}

}
