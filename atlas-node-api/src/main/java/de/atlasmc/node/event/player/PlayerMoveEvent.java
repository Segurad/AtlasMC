package de.atlasmc.node.event.player;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.Location;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerMoveEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final Location from;
	private final Location to;
	private boolean cancelled;
	
	public PlayerMoveEvent(Player player, Location from, Location to) {
		super(player);
		this.from = from;
		this.to = to;
	}
	
	public Location getFrom() {
		return from;
	}
	
	public Location getTo() {
		return to;
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
	
	public static enum MoveType {
		MOVE_AND_ROTATE,
		ROTATE,
		MOVE
	}
	
	public static enum MoveDirection {
		STRAIGHT_FORWARD(0, 0.5),
		STRAIGHT_BACKWARD(4, 0.5),
		STRAIGHT_LEFT(2, 0.5),
		STRAIGHT_RIGHT(2, 0.5),
		FORWARD_LEFT(0.59, 0.5),
		FORWARD_RIGHT(0.59, 0.5),
		BACKWARD_LEFT(3.41, 0.5),
		BACKWARD_RIGHT(3.41, 0.5),
		FORWARD(0, 1.85),
		BACKWARD(4, 1.85);
		
		private double val;
		private double toleranz;
		
		private MoveDirection(double val, double toleranz) {
			this.toleranz = toleranz;
		}
		
		final double getVal() {
			return val;
		}
		
		public double getToleranz() {
			return toleranz;
		}
	}

}
