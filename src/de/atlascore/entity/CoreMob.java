package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Mob;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;

public class CoreMob extends CoreLivingEntity implements Mob {

	/**
	 * 0x01 NoAI
	 * 0x02 Is left handed
	 * 0x04 Is aggressive
	 */
	protected static final MetaDataField<Byte>
	META_MOB_FLAGS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreLivingEntity.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_LEFT_HANDED = CharKey.of("LeftHanded"),
	NBT_NO_AI = CharKey.of("NoAI");
	
	static {
		NBT_FIELDS.setField(NBT_LEFT_HANDED, (holder, reader) -> {
			if (holder instanceof Mob) {
				((Mob) holder).setLeftHanded(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_NO_AI, (holder, reader) -> {
			if (holder instanceof Mob) {
				((Mob) holder).setAware(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreMob(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_MOB_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isAggressive() {
		return (metaContainer.getData(META_MOB_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public void setAggressive(boolean aggressive) {
		MetaData<Byte> data = metaContainer.get(META_MOB_FLAGS);
		data.setData((byte) (aggressive ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public boolean isAware() {
		return (metaContainer.getData(META_MOB_FLAGS) & 0x01) == 0x00;
	}

	@Override
	public void setAware(boolean aware) {
		MetaData<Byte> data = metaContainer.get(META_MOB_FLAGS);
		data.setData((byte) (!aware ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

	@Override
	public boolean isLeftHanded() {
		return (metaContainer.getData(META_MOB_FLAGS) & 0x02) == 0x02;
	}

	@Override
	public void setLeftHanded(boolean left) {
		MetaData<Byte> data = metaContainer.get(META_MOB_FLAGS);
		data.setData((byte) (left ? data.getData() | 0x02 : data.getData() & 0xFD));
	}

}
