package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ocelot;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreOcelot extends CoreAgeableMob implements Ocelot {

	protected static final MetaDataField<Boolean>
	META_OCELOT_TRUSTING = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreOcelot(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_OCELOT_TRUSTING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isTrusting() {
		return metaContainer.getData(META_OCELOT_TRUSTING);
	}

	@Override
	public void setTrusting(boolean trusting) {
		metaContainer.get(META_OCELOT_TRUSTING).setData(trusting);
	}

}
