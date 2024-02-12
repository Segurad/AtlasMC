package de.atlasmc.inventory.meta;

import de.atlasmc.entity.EntityType;

public interface SpawnEggMeta extends ItemMeta {
	
	public SpawnEggMeta clone();
	
	public EntityType getSpawnedType();
	
	public void setSpawnedType(EntityType type);

}
