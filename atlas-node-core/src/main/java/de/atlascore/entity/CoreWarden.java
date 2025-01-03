package de.atlascore.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Warden;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;

public class CoreWarden extends CoreMob implements Warden {

	protected static final MetaDataField<Integer> META_ANGER = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreWarden> NBT_FIELDS;
	
	private static final CharKey
	NBT_ANGER = CharKey.literal("anger"),
	NBT_UUID = CharKey.literal("uuid"),
	NBT_SUSPECTS = CharKey.literal("suspects");
	
	static {
		NBT_FIELDS = CoreMob.NBT_FIELDS.fork();
		NBTFieldSet<CoreWarden> anger = NBT_FIELDS.setSet(NBT_ANGER);
		anger.setField(NBT_SUSPECTS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				int val = 0;
				UUID uuid = null;
				while (reader.getType() != TagType.TAG_END) {
					CharSequence name = reader.getFieldName();
					if (NBT_ANGER.equals(name)) {
						val = reader.readIntTag();
					} else if (NBT_UUID.equals(name)) {
						uuid = reader.readUUID();
					} else {
						reader.skipTag();
					}
				}
				reader.readNextEntry();
				holder.setAnger(uuid, val);
			}
		});
	}
	
	private Map<UUID, Integer> entityAnger;
	
	public CoreWarden(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ANGER);
	}

	@Override
	public int getAnger(Entity entity) {
		if (entityAnger == null)
			return 0;
		return entityAnger.get(entity.getUUID());
	}

	@Override
	public int setAnger(Entity entity, int value) {
		if (entity == null)
			throw new IllegalArgumentException("Entity can not be null!");
		return setAnger(entity.getUUID(), value);
	}
	
	private int setAnger(UUID entity, int value) {
		if (value < 0)
			throw new IllegalArgumentException("Value can not be lower than 0: " + value);
		if (entityAnger == null) {
			if (value == 0)
				return 0;
			entityAnger = new HashMap<>();
		}
		int oldValue = entityAnger.put(entity, value);
		updateAnger(value, oldValue);
		return oldValue;	
	}

	@Override
	public int clearAnger(Entity entity) {
		if (entityAnger == null)
			return 0;
		int anger = entityAnger.remove(entity.getUUID());
		updateAnger(Integer.MIN_VALUE, anger);
		return anger;
	}

	@Override
	public int getAnger() {
		return metaContainer.getData(META_ANGER);
	}
	
	protected void updateAnger(int newVal, int oldVal) {
		MetaData<Integer> data = metaContainer.get(META_ANGER);
		int dataVal = data.getData();
		if (dataVal < newVal) {
			data.setData(newVal);
		} else if (dataVal == oldVal && oldVal != 0) {
			int max = 0;
			for (int i : entityAnger.values())
				if (i > max)
					max = i;
			data.setData(max);
		}
	}
	
	@Override
	protected NBTFieldSet<? extends CoreLivingEntity> getFieldSetRoot() {
		return NBT_FIELDS;
	}

}
