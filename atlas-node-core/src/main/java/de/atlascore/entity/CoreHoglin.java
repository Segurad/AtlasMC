package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Hoglin;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreHoglin extends CoreAgeableMob implements Hoglin {

	protected static final MetaDataField<Boolean>
	META_IMMUNE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	private boolean huntable = true;
	
	public CoreHoglin(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IMMUNE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isImmune() {
		return metaContainer.getData(META_IMMUNE);
	}

	@Override
	public void setImmune(boolean immune) {
		metaContainer.get(META_IMMUNE).setData(immune);	
	}

	@Override
	public void setHuntable(boolean huntable) {
		this.huntable = huntable;	
	}

	@Override
	public boolean isHuntable() {
		return huntable;
	}
	
}
