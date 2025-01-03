package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.AbstractArrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractArrow extends CoreAbstractProjectile implements AbstractArrow {

	protected static final MetaDataField<Byte> 
	META_ABSTRACT_ARROW_FLAGS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_PIERCING_LEVEL = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	protected static final NBTFieldSet<CoreAbstractArrow> NBT_FIELDS;
	
	protected static final CharKey
	NBT_CRIT = CharKey.literal("crit"),
	NBT_DAMAGE = CharKey.literal("damage"),
//	NBT_IN_BLOCK_STATE = "inBlockState", TODO unused in block state
//	NBT_X_TILE = "xTile",
//	NBT_Y_TILE = "yTile",
//	NBT_Z_TILE = "zTile",
//	NBT_IN_GROUND = "inGround",
	NBT_LIFE = CharKey.literal("life"),
	NBT_PICKUP = CharKey.literal("pickup"),
	NBT_SHAKE = CharKey.literal("shake");
	
	static {
		NBT_FIELDS = CoreAbstractProjectile.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_CRIT, (holder, reader) -> {
			holder.setCritical(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_DAMAGE, (holder, reader) -> {
			holder.setDamage(reader.readDoubleTag());
		});
		NBT_FIELDS.setField(NBT_LIFE, (holder, reader) -> {
			holder.setLifeTime(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_PICKUP, (holder, reader) -> {
			holder.setPickupable(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SHAKE, (holder, reader) -> {
			holder.setShakeOnImpact(reader.readByteTag() == 1);
		});
	}
	
	private double damage;
	private boolean pickupable;
	private boolean shakeOnImpact;
	private int lifetime = -1;
	
	public CoreAbstractArrow(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAbstractArrow> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ABSTRACT_ARROW_FLAGS);
		metaContainer.set(META_PIERCING_LEVEL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isCritical() {
		return (metaContainer.getData(META_ABSTRACT_ARROW_FLAGS) & 0x1) == 0x1;
	}

	@Override
	public void setCritical(boolean critical) {
		MetaData<Byte> data = metaContainer.get(META_ABSTRACT_ARROW_FLAGS);
		data.setData((byte) (data.getData() | 0x1));
	}
	
	@Override
	public int getPiercingLevel() {
		return metaContainer.getData(META_PIERCING_LEVEL);
	}
	
	@Override
	public void setPiercingLevel(int level) {
		metaContainer.get(META_PIERCING_LEVEL).setData((byte) level);
	}
	
	@Override
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	@Override
	public double getDamage() {
		return damage;
	}

	@Override
	public void setShakeOnImpact(boolean shake) {
		this.shakeOnImpact = shake;
	}
	
	@Override
	public boolean isShakingOnImpact() {
		return shakeOnImpact;
	}

	@Override
	public void setLifeTime(int ticks) {
		this.lifetime = ticks;
	}
	
	@Override
	public int getLifeTime() {
		return lifetime;
	}

	@Override
	public void setPickupable(boolean pickupable) {
		this.pickupable = pickupable;
	}
	
	@Override
	public boolean isPickupable() {
		return pickupable;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isCritical())
			writer.writeByteTag(NBT_CRIT, true);
		writer.writeDoubleTag(NBT_DAMAGE, getDamage());
		writer.writeShortTag(NBT_LIFE, getLifeTime());
		if (isPickupable())
			writer.writeByteTag(NBT_PICKUP, true);
		if (isShakingOnImpact())
			writer.writeByteTag(NBT_SHAKE, true);
	}

}
