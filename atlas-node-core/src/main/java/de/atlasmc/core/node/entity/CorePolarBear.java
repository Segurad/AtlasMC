package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.PolarBear;
import de.atlasmc.node.entity.metadata.type.MetaData;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CorePolarBear extends CoreAgeableMob implements PolarBear {

	protected static final MetaDataField<Boolean>
	META_IS_STANDING_UP = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	private int angerTime;
	
	public CorePolarBear(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_STANDING_UP);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isStandingUp() {
		return metaContainer.getData(META_IS_STANDING_UP);
	}

	@Override
	public void setStandingUp(boolean standing) {
		metaContainer.get(META_IS_STANDING_UP).setData(standing);
	}

	@Override
	public boolean isAngry() {
		return (metaContainer.getData(META_MOB_FLAGS) & FLAG_IS_ANGRY) == FLAG_IS_ANGRY;
	}

	@Override
	public void setAngry(boolean angry) {
		MetaData<Byte> data = metaContainer.get(META_MOB_FLAGS);
		data.setData((byte) (angry ? data.getData() | FLAG_IS_ANGRY : data.getData() & ~FLAG_IS_ANGRY));
	}

	@Override
	public int getAngerTime() {
		return angerTime;
	}

	@Override
	public void setAngerTime(int ticks) {
		this.angerTime = ticks;
	}

}
