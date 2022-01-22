package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vex;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreVex extends CoreMob implements Vex {

	protected static final MetaDataField<Byte>
	META_VEX_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreVex(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		return metaContainer.getData(META_VEX_FLAGS) == 0x01;
	}

	@Override
	public void setAttacking(boolean attacking) {
		metaContainer.get(META_VEX_FLAGS).setData((byte) (attacking ? 0x01 : 0x00));	
	}

}
