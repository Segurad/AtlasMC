package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.event.AbstractServerEvent;

public abstract class EntityEvent extends AbstractServerEvent {

	private final Entity entity;
	
	public EntityEvent(Entity entity) {
		super(entity.getServer());
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}

}
