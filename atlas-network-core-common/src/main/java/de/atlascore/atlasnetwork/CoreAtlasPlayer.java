package de.atlascore.atlasnetwork;

import java.sql.Date;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHandler;

/**
 * Stores the profile data of a player
 */
public class CoreAtlasPlayer implements AtlasPlayer {
	
	private final CorePlayerProfileHandler handler;
	private final int id;
	private final String mojangName;
	private final String internalName;
	private final UUID mojangUUID;
	private final UUID internalUUID;
	private final Date firstJoin;
	private volatile Date lastJoin;
	private volatile Proxy originProxy;
	private volatile Proxy proxy;
	private volatile Server currentServer;
	private volatile PermissionHandler permhandler;
	
	public CoreAtlasPlayer(CorePlayerProfileHandler handler, int id, String mojangName, UUID mojangUUID, String name, UUID uuid, Date firstJoin, Date lastJoin) {
		this.handler = handler;
		this.id = id;
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

	public Proxy getOriginProxy() {
		return originProxy;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public Server getCurrentServer() {
		return currentServer;
	}

	public UUID getInternalUUID() {
		return internalUUID;
	}

	public void setInternalUUID(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		handler.updateInternalUUID(this, uuid);
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		handler.updateInternalName(this, name);
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void sendToServer(Server server) {
		// TODO Auto-generated 
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return permhandler;
	}

	public void setPermissionHandler(PermissionHandler handler) {
		this.permhandler = handler;
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		return permhandler != null ? permhandler.getPermission(permission, allowWildcards) : null;			
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
	
	public void setLastJoin(Date date) {
		if (date == null)
			throw new IllegalArgumentException("Date can not be null!");
		this.lastJoin = date;	
		handler.updateLastJoind(this, date);
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		// TODO Auto-generated method stub
		
	}

}
