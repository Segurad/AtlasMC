package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Parrot;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreParrot extends CoreTameable implements Parrot {

	protected static final MetaDataField<Integer>
	META_PARROT_TYPE = new MetaDataField<>(CoreTameable.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreTameable.LAST_META_INDEX+1;
	
	public CoreParrot(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PARROT_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Type getParrotType() {
		return Type.getByID(metaContainer.getData(META_PARROT_TYPE));
	}

	@Override
	public void setParrotType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_PARROT_TYPE).setData(type.getID());
	}

}
