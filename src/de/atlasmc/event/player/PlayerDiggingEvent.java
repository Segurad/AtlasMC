package de.atlasmc.event.player;

import de.atlasmc.Location;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerDiggingEvent extends PlayerEvent implements Cancellable {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private DiggingStatus status;
	private int time;
	private final Location loc;
	private BlockFace face;
	private boolean cancelled;
	
	public PlayerDiggingEvent(Player player, DiggingStatus status, int time, Location loc, BlockFace face) {
		super(player);
		this.status = status;
		this.time = time;
		this.face = face;
		this.loc = loc;
	}

	public DiggingStatus getStatus() {
		return status;
	}
	
	/**
	 * Return the time in milliseconds the player had dug<br>
	 * Always 0 if status is {@link DiggingStatus#START_DIGGING}
	 * @return dig time
	 */
	public int getTime() {
		return time;
	}
	
	public void setStatus(DiggingStatus status) {
		this.status = status;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public BlockFace getFace() {
		return face;
	}
	
	public void setFace(BlockFace face) {
		this.face = face;
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
