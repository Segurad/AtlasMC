package de.atlascore.atlasnetwork;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutChatMessage;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHandler;

/**
 * Stores the profile data of a player
 */
public class CoreAtlasPlayer implements AtlasPlayer {
	
	private final CorePlayerProfileHandler handler;
	private final int id;
	private final String mojangName;
	private String internalName;
	private final UUID mojangUUID;
	private UUID internalUUID;
	private Proxy originProxy;
	private Proxy proxy;
	private Server currentServer;
	private PermissionHandler permhandler;
	private PlayerConnection con;
	
	private int revCount;
	private volatile boolean changed;
	
	public CoreAtlasPlayer(CorePlayerProfileHandler handler, int id, String mojangName, UUID mojangUUID, String name, UUID uuid) {
		this.handler = handler;
		this.id = id;
		this.mojangName = mojangName;
		this.mojangUUID = mojangUUID;
		this.internalName = name;
		this.internalUUID = uuid;
	}
	
	/**
	 * Method to mark the removal of a reverence and remove from cache if no references are present
	 */
	final void removeRev() {
		if (--revCount != 0)
			return;
		save();
		synchronized (this) {
			if (revCount > 0)
				return;
			handler.removeCached(this);
		}
	}
	
	/**
	 * Saves the profile if changes are present
	 */
	public void save() {
		if (!changed)
			return;
		synchronized (this) {
			if (!changed)
				return;
			handler.save(this);
			changed = false;
		}
	}

	/**
	 * Creates a reverence of this profile and makes sure it is cached
	 * @return reverence
	 */
	public synchronized final AtlasPlayer createRev() {
		revCount++;
		if (revCount <= 0)
			handler.cache(this);
		return new CoreAtlasPlayerWrapper(this);
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
		this.internalUUID = uuid;
		this.changed = true;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		this.internalName = name;
		this.changed = changed;
	}

	public int getID() {
		return id;
	}

	@Override
	public void sendToServer(Server server) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return permhandler;
	}

	@Override
	public void setPermissionHandler(PermissionHandler handler) {
		this.permhandler = handler;
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		return permhandler != null ? permhandler.getPermission(permission, allowWildcards) : null;			
	}

	@Override
	public void sendMessage(String message) {
		sendMessage(message, ChatType.SYSTEM);
	}

	@Override
	public void sendMessage(Chat chat, ChatType type) {
		if (chat == null)
			throw new IllegalArgumentException("Chat can not be null!");
		sendMessage(chat.getText(), type);
	}
	
	private void sendMessage(String message, ChatType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		if (con != null) {
			if (type.ordinal() < con.getSettings().getChatMode().ordinal())
				return;
			PacketOutChatMessage packet = new PacketOutChatMessage();
			packet.setMessage(message);
			packet.setType(type);
			con.sendPacked(packet);
		} else {
			// TODO send message to player within network
		}
	}

	@Override
	public PlayerConnection getConnection() {
		return con;
	}

	@Override
	public Player getPlayer() {
		return con != null ? con.getPlayer() : null;
	}

}
