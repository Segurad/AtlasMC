package de.atlasmc.core.node.entity;

import java.util.UUID;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Tameable;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreTameable extends CoreAgeableMob implements Tameable {
	
	protected static final int
	FLAG_IS_SITTING = 0x01,
	FLAG_IS_TAMED = 0x04;
	
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is tamed
	 */
	protected static final MetaDataField<Byte>
	META_TAMEABLE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	public CoreTameable(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TAMEABLE_FLAGS);
		metaContainer.set(META_OWNER);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isSitting() {
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & FLAG_IS_SITTING) == FLAG_IS_SITTING;
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		data.setData((byte) (sitting ? data.getData() | FLAG_IS_SITTING : data.getData() & ~FLAG_IS_SITTING));
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & FLAG_IS_TAMED) == FLAG_IS_TAMED;
	}

	@Override
	public void setTamed(boolean tamed) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		data.setData((byte) (tamed ? data.getData() | FLAG_IS_TAMED : data.getData() & ~FLAG_IS_TAMED));
	}

	@Override
	public UUID getOwner() {
		return metaContainer.getData(META_OWNER);
	}

	@Override
	public void setOwner(UUID owner) {
		metaContainer.get(META_OWNER).setData(owner);
	}

}
