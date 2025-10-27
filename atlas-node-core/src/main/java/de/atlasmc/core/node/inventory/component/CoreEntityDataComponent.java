package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EntityDataComponent;

public class CoreEntityDataComponent extends AbstractItemComponent implements EntityDataComponent {

	private Entity entity;
	
	public CoreEntityDataComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreEntityDataComponent clone() {
		CoreEntityDataComponent clone = (CoreEntityDataComponent) super.clone();
		if (entity != null)
			clone.entity = entity; // TODO copy entity
		return clone;
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
