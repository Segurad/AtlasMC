package de.atlasmc.node.event.entity;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.event.AbstractServerEvent;

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
