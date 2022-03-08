package de.atlasmc.inventory.meta;

import de.atlasmc.entity.EntityType;

public interface SpawnEggMeta extends ItemMeta {
	
	public SpawnEggMeta clone();
	
	@Override
	public default Class<? extends SpawnEggMeta> getInterfaceClass() {
		return SpawnEggMeta.class;
	}
	
	public EntityType getSpawnedType();
	
	public void setSpawnedType(EntityType type);

}
