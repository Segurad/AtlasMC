package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AgeableMob;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAgeableMob extends CoreMob implements AgeableMob {

	protected static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_AGE = CharKey.literal("Age"),
	NBT_KEEP_BABY = CharKey.literal("KeepBaby"), // optional non standard nbt to set this entity as baby even if the age is 0 or greater
	NBT_FORCE_AGE = CharKey.literal("ForceAge"),
	NBT_IN_LOVE = CharKey.literal("InLove"),
	NBT_LOVE_CAUSE = CharKey.literal("LoveCauses");
	
	static {
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			if (!(holder instanceof CoreAgeableMob)) {
				reader.skipTag();
				return;
			}
			CoreAgeableMob mob = (CoreAgeableMob) holder;
			int age = reader.readIntTag();
			if (age < 0)
				mob.setBaby(true);
			mob.setAge(age);
		});
		NBT_FIELDS.setField(NBT_KEEP_BABY, (holder, reader) -> {
			if (holder instanceof AgeableMob) {
				((AgeableMob) holder).setBaby(true);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FORCE_AGE, NBTField.skip()); // TODO do research how this field works
		NBT_FIELDS.setField(NBT_IN_LOVE, (holder, reader) -> {
			if (holder instanceof AgeableMob) {
				((AgeableMob) holder).setInLove(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LOVE_CAUSE, (holder, reader) -> {
			if (holder instanceof AgeableMob) {
				((AgeableMob) holder).setLoveCause(reader.readUUID());
			} else reader.skipTag();
		});
	}
	
	private int age;
	private int inLove;
	private UUID loveCause;
	
	public CoreAgeableMob(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BABY);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isBaby() {
		return metaContainer.getData(META_IS_BABY);
	}

	@Override
	public void setBaby(boolean baby) {
		metaContainer.get(META_IS_BABY).setData(baby);
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setInLove(int time) {
		this.inLove = time;
	}

	@Override
	public int isInLove() {
		return inLove;
	}

	@Override
	public void setLoveCause(UUID uuid) {
		this.loveCause = uuid;
	}

	@Override
	public UUID getLoveCause() {
		return loveCause;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_AGE, getAge());
		if (isInLove() > 0) {
			writer.writeIntTag(NBT_IN_LOVE, isInLove());
			writer.writeUUID(NBT_LOVE_CAUSE, loveCause);
		}
	}
	
}
