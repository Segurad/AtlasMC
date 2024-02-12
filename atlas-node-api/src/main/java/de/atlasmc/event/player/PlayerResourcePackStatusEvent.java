package de.atlasmc.event.player;

import java.util.List;

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

		private static List<ResourcePackStatus> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static ResourcePackStatus getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<ResourcePackStatus> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	
}
