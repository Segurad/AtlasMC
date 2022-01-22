package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.AbstractHorse;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreAbstractHorse extends CoreAgeableMob implements AbstractHorse {

	/**
	 * 0x02 - Is Tame<br>
	 * 0x04 - Is saddled<br>
	 * 0x08 - Has bred<br>
	 * 0x10 - Is eating<br>
	 * 0x20 - Is rearing<br>
	 * 0x40 - Is mouth open
	 */
	protected static final MetaDataField<Byte>
	META_HORSE_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<UUID>
	META_HORSE_OWNER = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, null, MetaDataType.OPT_UUID);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	public CoreAbstractHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HORSE_FLAGS);
		metaContainer.set(META_HORSE_OWNER);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isSaddled() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public boolean hasBred() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x08) == 0x08;
	}

	@Override
	public boolean isEating() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public boolean isRearing() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x20) == 0x20;
	}

	@Override
	public boolean isMouthOpen() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x40) == 0x40;
	}

	@Override
	public UUID getOwner() {
		return metaContainer.getData(META_HORSE_OWNER);
	}

	@Override
	public boolean isTamed() {
		return (metaContainer.getData(META_HORSE_FLAGS) & 0x02) == 0x02;
	}

	@Override
	public void setTamed(boolean tamed) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (tamed ? data.getData() | 0x02 : data.getData() & 0xFD));
	}

	@Override
	public void setSaddled(boolean saddled) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (saddled ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public void setBred(boolean bred) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (bred ? data.getData() | 0x08 : data.getData() & 0xF7));
	}

	@Override
	public void setEating(boolean eating) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (eating ? data.getData() | 0x10 : data.getData() & 0xEF));
	}

	@Override
	public void setRearing(boolean rearing) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (rearing ? data.getData() | 0x20 : data.getData() & 0xDF));
	}

	@Override
	public void setMouthOpen(boolean open) {
		MetaData<Byte> data = metaContainer.get(META_HORSE_FLAGS);
		data.setData((byte) (open ? data.getData() | 0x40 : data.getData() & 0xBF));
	}

	@Override
	public void setOwner(UUID owner) {
		metaContainer.get(META_HORSE_OWNER).setData(owner);
	}

}
