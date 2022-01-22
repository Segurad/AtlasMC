package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Tameable;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreTameable extends CoreAgeableMob implements Tameable {
	
	/**
	 * 0x01 - Is sitting<br>
	 * 0x04 - Is tamed
	 */
	protected static final MetaDataField<Byte>
	META_TAMEABLE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	public CoreTameable(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & 0x01) == 0x01;
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		data.setData((byte) (sitting ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_TAMEABLE_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public void setTamed(boolean tamed) {
		MetaData<Byte> data = metaContainer.get(META_TAMEABLE_FLAGS);
		data.setData((byte) (tamed ? data.getData() | 0x04 : data.getData() & 0xFB));
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
