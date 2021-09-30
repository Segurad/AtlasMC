package de.atlascore.entity;

import de.atlasmc.Location;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FishingHook;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataType;

import java.util.UUID;

public class CoreFishingHook extends CoreEntity implements FishingHook {

	protected static int
	META_HOCKED_ENTITY = 8,
	META_CATCHABLE = 9;
	
	private Entity hooked;
	
	public CoreFishingHook(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_CATCHABLE, MetaDataType.INT, 0));
		metaContainer.set(new MetaData<>(META_CATCHABLE, MetaDataType.BOOLEAN, false));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return super.getMetaContainerSize()+2;
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.FISH_HOOK;
	}

	@Override
	public Entity getHookedEntity() {
		return hooked;
	}

	@Override
	public void setHookedEntity(Entity hooked) {
		this.hooked = hooked;
		int id = 0;
		if (hooked != null)
			id = hooked.getID()+1;
		metaContainer.get(META_HOCKED_ENTITY, MetaDataType.INT).setData(id);
	}

}
