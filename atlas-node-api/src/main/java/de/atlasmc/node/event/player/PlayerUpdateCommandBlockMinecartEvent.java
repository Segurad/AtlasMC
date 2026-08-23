package de.atlasmc.node.event.player;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerUpdateCommandBlockMinecartEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private int entityID;
	private Entity entity;
	private final String command;
	private boolean trackoutput;
	private boolean cancelled;
	
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
	 * Returns a Entity or null if no valid Entity is present
	 * @return Entity or null
	 */
	public Entity getEntity() {
		if (entity == null && entityID != -1) {
			entity = getPlayer().getWorld().getEntity(entityID);
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
