package de.atlasmc.node.event.entity;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.entity.AgeableMob;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.event.ServerHandlerList;

/**
 * Called when a {@link AgeableMob} turns into a adult through the age value.<br>
 * If the event is cancelled the age value will remain at 0 but the Entity is kept as baby.
 */
public class EntityGrowEvent extends EntityEvent implements Cancellable {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();

	private boolean cancelled;
	
	public EntityGrowEvent(Entity entity) {
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

}
