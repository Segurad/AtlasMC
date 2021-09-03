package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class SignChangeEvent extends BlockEvent implements Cancellable {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();

	private final Player player;
	private final String[] lines;
	private boolean cancelled;
	
	public SignChangeEvent(Block block, Player player, String[] lines) {
		super(block);
		this.player = player;
		this.lines = lines;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String[] getLines() {
		return lines;
	}
	
	public void setLine(int line, String text) {
		lines[line] = text;
	}
	
	public String getLine(int line) {
		return lines[line];
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
