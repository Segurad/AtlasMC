package de.atlasmc.event.player;

import de.atlasmc.Location;
import de.atlasmc.entity.Player;
import de.atlasmc.event.AbstractServerEvent;
import de.atlasmc.event.ServerHandlerList;

public class PlayerMoveEvent extends AbstractServerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final Location from, to;
	
	public PlayerMoveEvent(Player player, Location from, Location to) {
		super(player.getServer());
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
		
		private double val, toleranz;
		
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
