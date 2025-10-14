package de.atlasmc.node.event.player;

import java.util.UUID;

import de.atlasmc.IDHolder;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerResourcePackStatusEvent extends PlayerEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final UUID uuid;
	private final ResourcePackStatus status;
	
	public PlayerResourcePackStatusEvent(Player player, UUID uuid, ResourcePackStatus status) {
		super(player);
		this.status = status;
		this.uuid = uuid;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public ResourcePackStatus getStatus() {
		return status;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

	public static enum ResourcePackStatus implements IDHolder {
		
		SUCCESSFULLY_DOWNLOADED,
		DECLINED,
		FAILED_DOWNLOAD,
		ACCEPTED,
		DOWNLOADED,
		INVALID_URL,
		FAILED_RELOAD,
		DISCARDED;

		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
