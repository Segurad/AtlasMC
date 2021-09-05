package de.atlasmc.event.player;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.MinecartCommandBlock;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerUpdateCommandBlockMinecartEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private int entityID;
	private MinecartCommandBlock entity;
	private final String command;
	private boolean trackoutput, cancelled;
	
	public PlayerUpdateCommandBlockMinecartEvent(Player player, int entityID, String command, boolean trackoutput) {
		super(player);
		this.entityID = entityID;
		this.command = command;
		this.trackoutput = trackoutput;
	}

	/**
	 * Return the EntityID or -1 if the EntityID is not valid [set by {@link #getEntity()}]
	 * @return id or -1
	 */
	public int getEntityID() {
		return entityID;
	}
	
	/**
	 * Returns a {@link MinecartCommandBlock} or null if no valid Entity is present
	 * @return Entity or null
	 */
	public MinecartCommandBlock getEntity() {
		if (entity == null && entityID != -1) {
			Entity ent = getPlayer().getWorld().getEntity(entityID);
			if (ent instanceof MinecartCommandBlock) 
				entity = (MinecartCommandBlock) ent;
			else
				entityID = -1;
		}
		return entity;
	}
	
	public String getCommand() {
		return command;
	}
	
	public boolean getTrackoutput() {
		return trackoutput;
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
