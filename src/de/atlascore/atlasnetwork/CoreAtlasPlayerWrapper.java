package de.atlascore.atlasnetwork;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHandler;

/**
 * Wrapper for a {@link CoreAtlasPlayer} to determine when the profile can be saved and removed from cache
 */
public class CoreAtlasPlayerWrapper implements AtlasPlayer {

	private final CoreAtlasPlayer profile;
	
	public CoreAtlasPlayerWrapper(CoreAtlasPlayer profile) {
		this.profile = profile;
	}
	
	@Override
	public String getMojangName() {
		return profile.getMojangName();
	}

	@Override
	public UUID getMojangUUID() {
		return profile.getMojangUUID();
	}

	@Override
	public Proxy getOriginProxy() {
		return profile.getOriginProxy();
	}

	@Override
	public Proxy getProxy() {
		return profile.getProxy();
	}

	@Override
	public void sendToServer(Server server) {
		profile.sendToServer(server);
	}

	@Override
	public Server getCurrentServer() {
		return profile.getCurrentServer();
	}

	@Override
	public UUID getInternalUUID() {
		return profile.getInternalUUID();
	}

	@Override
	public void setInternalUUID(UUID uuid) {
		profile.setInternalUUID(uuid);
	}

	@Override
	public String getInternalName() {
		return profile.getInternalName();
	}

	@Override
	public void setInternalName(String name) {
		profile.setInternalName(name);
	}
	
	@Override
	protected void finalize() throws Throwable {
		profile.removeRev();
	}

	@Override
	public int hashCode() {
		return profile.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreAtlasPlayerWrapper other = (CoreAtlasPlayerWrapper) obj;
		return profile.equals(other.profile);
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return profile.getPermissionHandler();
	}

	@Override
	public void setPermissionHandler(PermissionHandler handler) {
		profile.setPermissionHandler(handler);
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		return profile.getPermission(permission, allowWildcards);
	}

	@Override
	public void sendMessage(String message) {
		profile.sendMessage(message);
	}

	@Override
	public void sendMessage(Chat chat, ChatType type) {
		profile.sendMessage(chat, type);
	}

	@Override
	public PlayerConnection getConnection() {
		return profile.getConnection();
	}

	@Override
	public Player getPlayer() {
		return profile.getPlayer();
	}

}
