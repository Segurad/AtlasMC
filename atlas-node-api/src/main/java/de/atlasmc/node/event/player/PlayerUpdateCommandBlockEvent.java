package de.atlasmc.node.event.player;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.block.tile.CommandBlock.Mode;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerUpdateCommandBlockEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final WorldLocation loc;
	private String command;
	private Mode mode;
	private boolean trackoutput;
	private boolean conditional;
	private boolean alwaysactive;
	private boolean cancelled;
	
	public PlayerUpdateCommandBlockEvent(Player player, WorldLocation loc, String command, Mode mode, boolean trackoutput, boolean conditional, boolean alwaysactive) {
		super(player);
		this.loc = loc;
		this.command = command;
		this.mode = mode;
		this.trackoutput = trackoutput;
		this.conditional = conditional;
		this.alwaysactive = alwaysactive;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public boolean getTrackOutput() {
		return trackoutput;
	}
	
	public boolean isConditional() {
		return conditional;
	}
	
	public boolean isAlwaysactive() {
		return alwaysactive;
	}
	
	public WorldLocation getLocation() {
		return loc;
	}
	
	public String getCommand() {
		return command;
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

}
