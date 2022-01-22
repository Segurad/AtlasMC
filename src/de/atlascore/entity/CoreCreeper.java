package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Creeper;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreCreeper extends CoreMob implements Creeper {
	
	protected static final MetaDataField<Integer>
	META_CREEPER_STATE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, -1, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_IS_CHARGED = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_IGNITED = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;

	private int fuzeTime = -1;
	
	public CoreCreeper(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CREEPER_STATE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getFuseTime() {
		return fuzeTime;
	}

	@Override
	public boolean isChared() {
		return metaContainer.getData(META_IS_CHARGED);
	}

	@Override
	public boolean isIgnited() {
		return metaContainer.getData(META_IS_IGNITED);
	}

	@Override
	public void setFuseTime(int fuze) {
		this.fuzeTime = fuze;
	}

	@Override
	public void setChared(boolean charged) {
		metaContainer.get(META_IS_CHARGED).setData(charged);
	}

	@Override
	public void setIgnited(boolean ignited) {
		metaContainer.get(META_IS_IGNITED).setData(ignited);
	}

	@Override
	public boolean isFusing() {
		return metaContainer.getData(META_CREEPER_STATE) == 1;
	}

	@Override
	public void setFusing(boolean fuzing) {
		metaContainer.get(META_CREEPER_STATE).setData(fuzing ? 1 : -1);
	}

}
