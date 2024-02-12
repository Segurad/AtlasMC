package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vex;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;

public class CoreVex extends CoreMob implements Vex {

	protected static final MetaDataField<Byte>
	META_VEX_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer<CoreVex> NBT_FIELDS;
	
	protected static final CharKey
	//NBT_BOUND_X = "BoundX", TODO unnecessary
	//NBT_BOUND_Y = "BoundY",
	//NBT_BOUND_Z = "BoundZ",
	NBT_LIFE_TICKS = CharKey.literal("LifeTicks");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_LIFE_TICKS, (holder, reader) -> {
			holder.setLifeTime(reader.readIntTag());
		});
	}
	
	private int lifetime = -1;
	
	public CoreVex(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreVex> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_VEX_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isAttacking() {
		return metaContainer.getData(META_VEX_FLAGS) == 0x01;
	}

	@Override
	public void setAttacking(boolean attacking) {
		metaContainer.get(META_VEX_FLAGS).setData((byte) (attacking ? 0x01 : 0x00));	
	}

	@Override
	public void setLifeTime(int ticks) {
		this.lifetime = ticks;
	}

	@Override
	public int getLifeTime() {
		return lifetime;
	}

}
