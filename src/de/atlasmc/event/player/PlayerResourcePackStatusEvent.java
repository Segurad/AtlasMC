package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class PlayerResourcePackStatusEvent extends PlayerEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final ResourcePackStatus status;
	
	public PlayerResourcePackStatusEvent(Player player, ResourcePackStatus status) {
		super(player);
		this.status = status;
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

	public static enum ResourcePackStatus {
		SUCCESSFULLY_LOADED,
		DECLINED,
		FAILED_DOWNLOAD,
		ACCEPTED;

		public static ResourcePackStatus getByID(int id) {
			return values()[id];
		}
	}
	
}
