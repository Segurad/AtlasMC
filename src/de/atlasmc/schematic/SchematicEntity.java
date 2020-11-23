package de.atlasmc.schematic;

import de.atlasmc.Location;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;

public class SchematicEntity implements SchematicObject {

	private EntityType type;
	//private EntityData data;

	public SchematicEntity(EntityType type) {
		this.type = type;
	}

	public SchematicEntity(Entity entity) {
		type = entity.getType();
	}

	public EntityType getType() {
		return type;
	}

	public void place(Location loc) {
		Entity ent = loc.getWorld().spawnEntity(loc, type);
	}

	@Override
	public boolean isAir() {
		return false;
	}
}
