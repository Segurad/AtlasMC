package de.atlascore.entity;

import de.atlasmc.entity.AbstractArrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

import java.io.IOException;
import java.util.UUID;

public abstract class CoreAbstractArrow extends CoreAbstractProjectile implements AbstractArrow {

	protected static final MetaDataField<Byte> 
	META_ABSTRACT_ARROW_FLAGS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_PIERCING_LEVEL = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_CRIT = "crit",
	NBT_DAMAGE = "damage",
//	NBT_IN_BLOCK_STATE = "inBlockState", TODO unused in block state
//	NBT_X_TILE = "xTile",
//	NBT_Y_TILE = "yTile",
//	NBT_Z_TILE = "zTile",
//	NBT_IN_GROUND = "inGround",
	NBT_LIFE = "life",
	NBT_PICKUP = "pickup",
	NBT_SHAKE = "shake";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractProjectile.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_CRIT, (holder, reader) -> {
			if (holder instanceof AbstractArrow) {
				((AbstractArrow) holder).setCritical(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_DAMAGE, (holder, reader) -> {
			if (holder instanceof AbstractArrow) {
				((AbstractArrow) holder).setDamage(reader.readDoubleTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LIFE, (holder, reader) -> {
			if (holder instanceof AbstractArrow) {
				((AbstractArrow) holder).setLifeTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_PICKUP, (holder, reader) -> {
			if (holder instanceof AbstractArrow) {
				((AbstractArrow) holder).setPickupable(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_SHAKE, (holder, reader) -> {
			if (holder instanceof AbstractArrow) {
				((AbstractArrow) holder).setShakeOnImpact(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private double damage;
	private boolean pickupable;
	private boolean shakeOnImpact;
	private int lifetime = -1;
	
	public CoreAbstractArrow(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
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
	public abstract ProjectileType getProjectileType();

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
