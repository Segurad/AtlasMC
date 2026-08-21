package de.atlasmc.core.node.entity;

import java.util.UUID;

import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Tameable;

public class CoreTameable extends CoreAgeableMob implements Tameable {
	
	protected static final int
	FLAG_IS_SITTING = 0x01,
	FLAG_IS_TAMED = 0x04;
	
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is tamed
	 */
	protected static final MetaDataField<Byte>
	META_TAMEABLE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, EntityMetaTypes.BYTE);
	protected static final MetaDataField<UUID>
	META_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, EntityMetaTypes.OPT_UUID);
	
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
	
	protected void setTameableFlag(int flag, boolean set) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		metaContainer.setData(META_TAMEABLE_FLAGS, value);
	}

	@Override
	public void setSitting(boolean sitting) {
		setTameableFlag(FLAG_IS_SITTING, sitting);
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & FLAG_IS_TAMED) == FLAG_IS_TAMED;
	}

	@Override
	public void setTamed(boolean tamed) {
		setTameableFlag(FLAG_IS_TAMED, tamed);
	}

	@Override
	public UUID getOwner() {
		return metaContainer.getData(META_OWNER);
	}

	@Override
	public void setOwner(UUID owner) {
		metaContainer.setData(META_OWNER, owner);
	}

}
