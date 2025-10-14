package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Rabbit;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.util.EnumUtil;

public class CoreRabbit extends CoreAgeableMob implements Rabbit {

	protected static final MetaDataField<Integer>
	META_RABBIT_TYPE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);

	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	public CoreRabbit(EntityType type) {
		super(type);
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
		return EnumUtil.getByID(Type.class, metaContainer.getData(META_RABBIT_TYPE));
	}

	@Override
	public void setRabbitType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_RABBIT_TYPE).setData(type.getID());		
	}

}
