package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ghast;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreGhast extends CoreMob implements Ghast {

	protected static final MetaDataField<Boolean>
	META_IS_ATTACKING = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	private int explosionPower;
	
	public CoreGhast(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_ATTACKING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isAttacking() {
		return metaContainer.getData(META_IS_ATTACKING);
	}

	@Override
	public void setAttacking(boolean attacking) {
		metaContainer.get(META_IS_ATTACKING).setData(attacking);		
	}

	@Override
	public void setExplosionPower(int power) {
		this.explosionPower = power;
	}

	@Override
	public int getExplosionPower() {
		return explosionPower;
	}

}
