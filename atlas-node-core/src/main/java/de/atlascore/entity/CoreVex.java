package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vex;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreVex extends CoreMob implements Vex {

	protected static final int
	FLAG_IS_ATTACKING = 0x01;
	
	protected static final MetaDataField<Byte>
	META_VEX_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	private int lifetime = -1;
	
	public CoreVex(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_VEX_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isAttacking() {
		return metaContainer.getData(META_VEX_FLAGS) == FLAG_IS_ATTACKING;
	}

	@Override
	public void setAttacking(boolean attacking) {
		metaContainer.get(META_VEX_FLAGS).setData((byte) (attacking ? FLAG_IS_ATTACKING : ~FLAG_IS_ATTACKING));	
	}

	@Override
	public void setLifeTime(int ticks) {
		this.lifetime = ticks;
	}

	@Override
	public int getLifeTime() {
		return lifetime;
	}

}
