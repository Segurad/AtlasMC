package de.atlasmc.core.master;

import java.net.InetAddress;
import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.Future;

import de.atlasmc.chat.Chat;
import de.atlasmc.network.permission.PermissionHandler;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.network.server.BaseServer;
import de.atlasmc.network.socket.AtlasSocket;
import de.atlasmc.permission.Permission;

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
	private volatile BaseServer currentServer;
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

	@Override
	public String getMojangName() {
		return mojangName;
	}

	@Override
	public UUID getMojangUUID() {
		return mojangUUID;
	}

	@Override
	public UUID getOriginSocketID() {
		var originProxy = this.originProxy;
		return originProxy != null ? originProxy.getUUID() : null;
	}

	@Override
	public UUID getSocketID() {
		var proxy = this.proxy;
		return proxy != null ? proxy.getUUID() : null;
	}

	@Override
	public UUID getCurrentServerID() {
		var currentServer = this.currentServer;
		return currentServer != null ? currentServer.getID() : null;
	}

	@Override
	public UUID getInternalUUID() {
		return internalUUID;
	}

	@Override
	public String getInternalName() {
		return internalName;
	}

	@Override
	public void setInternalName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		handler.updateInternalName(this, name);
	}

	@Override
	public Future<Boolean> sendToServer(BaseServer server) {
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
	public void sendMessage(Chat chat, boolean overlay) {
		if (chat == null)
			throw new IllegalArgumentException("Chat can not be null!");
		// TODO handle chat
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
