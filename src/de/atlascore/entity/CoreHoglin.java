package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Hoglin;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreHoglin extends CoreAgeableMob implements Hoglin {

	protected static final MetaDataField<Boolean>
	META_IMMUNE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreHoglin(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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

}
