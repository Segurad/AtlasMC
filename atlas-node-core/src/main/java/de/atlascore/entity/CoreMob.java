package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Mob;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreMob extends CoreLivingEntity implements Mob {

	protected static final int
	FLAG_NO_AI = 0x01,
	FLAG_IS_LEFT_HANDED = 0x02,
	FLAG_IS_ANGRY = 0x04;
	
	/**
	 * 0x01 NoAI
	 * 0x02 Is left handed
	 * 0x04 Is angry
	 */
	protected static final MetaDataField<Byte>
	META_MOB_FLAGS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreLivingEntity.LAST_META_INDEX+1;
	
	public CoreMob(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_MOB_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isLeftHanded() {
		return (metaContainer.getData(META_MOB_FLAGS) & FLAG_IS_LEFT_HANDED) == FLAG_IS_LEFT_HANDED;
	}

	@Override
	public void setLeftHanded(boolean left) {
		MetaData<Byte> data = metaContainer.get(META_MOB_FLAGS);
		data.setData((byte) (left ? data.getData() | FLAG_IS_LEFT_HANDED : data.getData() & ~FLAG_IS_LEFT_HANDED));
	}
	
	@Override
	public boolean hasNoAI() {
		return (metaContainer.getData(META_MOB_FLAGS) & FLAG_NO_AI) == FLAG_NO_AI;
	}
	
	@Override
	public void setNoAi(boolean ai) {
		MetaData<Byte> data = metaContainer.get(META_MOB_FLAGS);
		data.setData((byte) (ai ? data.getData() | FLAG_NO_AI : data.getData() & ~FLAG_NO_AI));
	}

}
