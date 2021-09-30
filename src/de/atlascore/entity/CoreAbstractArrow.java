package de.atlascore.entity;

import de.atlasmc.Location;
import de.atlasmc.entity.AbstractArrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataType;

import java.util.UUID;

public abstract class CoreAbstractArrow extends CoreEntity implements AbstractArrow {

	protected static final int
	META_ABSTRACT_ARROW_FLAGS = 8,
	META_PIERCING_LEVEL = 9;
	
	public CoreAbstractArrow(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_ABSTRACT_ARROW_FLAGS, MetaDataType.BYTE, (byte) 0));
		metaContainer.set(new MetaData<>(META_PIERCING_LEVEL, MetaDataType.BYTE, (byte) 0));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return super.getMetaContainerSize()+2;
	}

	@Override
	public abstract ProjectileType getProjectileType();

	@Override
	public boolean isCritical() {
		return (metaContainer.getData(META_ABSTRACT_ARROW_FLAGS, MetaDataType.BYTE) & 0x1) == 0x1;
	}

	@Override
	public boolean isInBlock() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setCritical(boolean critical) {
		MetaData<Byte> data = metaContainer.get(META_ABSTRACT_ARROW_FLAGS, MetaDataType.BYTE);
		data.setData((byte) (data.getData() | 0x1));
	}
	
	@Override
	public int getPiercingLevel() {
		return metaContainer.getData(META_PIERCING_LEVEL, MetaDataType.BYTE);
	}
	
	@Override
	public void setPiercingLevel(int level) {
		metaContainer.get(META_PIERCING_LEVEL, MetaDataType.BYTE).setData((byte) level);
	}

}
