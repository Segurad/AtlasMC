package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Rabbit;
import de.atlasmc.util.enums.EnumUtil;

public class CoreRabbit extends CoreAgeableMob implements Rabbit {

	protected static final MetaDataField<Integer>
	META_RABBIT_TYPE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, EntityMetaTypes.VAR_INT);

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
		metaContainer.setData(META_RABBIT_TYPE, type.getID());		
	}

}
