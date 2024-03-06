package de.atlasmc.event.command;

import de.atlasmc.command.CommandSender;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;

public class CommandEvent extends Event implements Cancellable {
	
	private static final HandlerList HANDLERS = new HandlerList();

	private final CommandSender sender;
	private String command;
	private boolean cancelled;
	
	public CommandEvent(CommandSender sender, String command, boolean async) {
		super(async);
		this.sender = sender;
		this.command = command;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
