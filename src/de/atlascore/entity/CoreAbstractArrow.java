package de.atlascore.entity;

import de.atlasmc.entity.AbstractArrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

import java.util.UUID;

public abstract class CoreAbstractArrow extends CoreAbstractProjectile implements AbstractArrow {

	protected static final MetaDataField<Byte> 
	META_ABSTRACT_ARROW_FLAGS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_PIERCING_LEVEL = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	public CoreAbstractArrow(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ABSTRACT_ARROW_FLAGS);
		metaContainer.set(META_PIERCING_LEVEL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public abstract ProjectileType getProjectileType();

	@Override
	public boolean isCritical() {
		return (metaContainer.getData(META_ABSTRACT_ARROW_FLAGS) & 0x1) == 0x1;
	}

	@Override
	public boolean isInBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setCritical(boolean critical) {
		MetaData<Byte> data = metaContainer.get(META_ABSTRACT_ARROW_FLAGS);
		data.setData((byte) (data.getData() | 0x1));
	}
	
	@Override
	public int getPiercingLevel() {
		return metaContainer.getData(META_PIERCING_LEVEL);
	}
	
	@Override
	public void setPiercingLevel(int level) {
		metaContainer.get(META_PIERCING_LEVEL).setData((byte) level);
	}

}
