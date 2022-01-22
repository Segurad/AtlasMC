package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Phantom;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CorePhantom extends CoreMob implements Phantom {

	protected static final MetaDataField<Integer>
	META_PHANTOM_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	public CorePhantom(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PHANTOM_SIZE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getSize() {
		return metaContainer.getData(META_PHANTOM_SIZE);
	}

	@Override
	public void setSize(int size) {
		metaContainer.get(META_PHANTOM_SIZE).setData(size);	
		// TODO alter hitbox (horizontal=0.9 + 0.2*size and vertical=0.5 + 0.1 * i)
	}

}
