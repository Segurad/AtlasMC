package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Mooshroom;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreMooshroom extends CoreCow implements Mooshroom {

	protected static final MetaDataField<String>
	META_SHROOM_TYPE = new MetaDataField<>(CoreCow.LAST_META_INDEX+1, Variant.RED.getNameID(), MetaDataType.STRING);
	
	protected static final int LAST_META_INDEX = CoreCow.LAST_META_INDEX+1;
	
	public CoreMooshroom(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SHROOM_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Variant getVariant() {
		return Variant.getByNameID(metaContainer.getData(META_SHROOM_TYPE));
	}

	@Override
	public void setVariant(Variant variant) {
		if (variant == null)
			throw new IllegalArgumentException("Variant can not be null!");
		metaContainer.get(META_SHROOM_TYPE).setData(variant.getNameID());
	}

}
