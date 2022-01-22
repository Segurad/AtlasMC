package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Slime;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreSlime extends CoreMob implements Slime {

	protected static final MetaDataField<Integer>
	META_SLIME_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 1, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CoreSlime(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SLIME_SIZE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getSize() {
		return metaContainer.getData(META_SLIME_SIZE);
	}

	@Override
	public void setSize(int size) {
		metaContainer.get(META_SLIME_SIZE).setData(size);		
	}

}
