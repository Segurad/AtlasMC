package de.atlasmc.event.player;

import de.atlasmc.Location;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerDiggingEvent extends PlayerEvent implements Cancellable {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private DiggingStatus status;
	private final Location loc;
	private BlockFace face;
	private boolean cancelled;
	private float progressPerTick;
	
	public PlayerDiggingEvent(Player player, DiggingStatus status, Location loc, BlockFace face) {
		super(player);
		this.status = status;
		this.face = face;
		this.loc = loc;
	}

	public float getProgressPerTick() {
		return progressPerTick;
	}
	
	public void setProgressPerTick(float progressPerTick) {
		this.progressPerTick = progressPerTick;
	}
	
	public DiggingStatus getStatus() {
		return status;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public BlockFace getFace() {
		return face;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}
	
	public static enum DiggingStatus {
		START_DIGGING,
		CANCELLED_DIGGING,
		FINISHED_DIGGING
	}

}
