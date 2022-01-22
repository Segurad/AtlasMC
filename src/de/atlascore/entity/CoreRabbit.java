package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Rabbit;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreRabbit extends CoreAgeableMob implements Rabbit {

	protected static final MetaDataField<Integer>
	META_RABBIT_TYPE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.INT);

	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreRabbit(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_RABBIT_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Type getRabbitType() {
		return Type.getByID(metaContainer.getData(META_RABBIT_TYPE));
	}

	@Override
	public void setRabbitType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_RABBIT_TYPE).setData(type.getID());		
	}

}
