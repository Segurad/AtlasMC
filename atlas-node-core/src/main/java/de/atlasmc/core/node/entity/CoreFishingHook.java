package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.FishingHook;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreFishingHook extends CoreAbstractProjectile implements FishingHook {

	protected static final MetaDataField<Integer> 
	META_HOCKED_ENTITY = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_CATCHABLE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	private Entity hooked;
	
	public CoreFishingHook(EntityType type) {
		super(type);
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
