package de.atlascore.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FishingHook;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

import java.util.UUID;

public class CoreFishingHook extends CoreAbstractProjectile implements FishingHook {

	protected static final MetaDataField<Integer> 
	META_HOCKED_ENTITY = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_CATCHABLE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	private Entity hooked;
	
	public CoreFishingHook(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HOCKED_ENTITY);
		metaContainer.set(META_CATCHABLE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
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
		metaContainer.get(META_HOCKED_ENTITY).setData(id);
	}

}
