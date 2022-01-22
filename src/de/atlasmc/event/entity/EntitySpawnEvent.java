package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.world.World;

/**
 * Called before a {@link Entity} is spawned at a {@link World}
 */
public class EntitySpawnEvent extends EntityEvent implements Cancellable {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private boolean cancelled;
	
	public EntitySpawnEvent(Entity entity) {
		super(entity);
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
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

}
