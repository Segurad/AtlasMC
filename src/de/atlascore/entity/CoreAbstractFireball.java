package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Vector;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.AbstractArrow;
import de.atlasmc.entity.AbstractFireball;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public abstract class CoreAbstractFireball extends CoreAbstractProjectile implements AbstractFireball {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_DIRECTION = CharKey.of("direction"),
	NBT_EXPLOSION_POWER = CharKey.of("ExplosionPower"),
	NBT_POWER = CharKey.of("power"),
	NBT_LIFE = CharKey.of("life");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractProjectile.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_DIRECTION, (holder, reader) -> {
			if (holder instanceof AbstractFireball) {
				double x;
				double y;
				double z;
				reader.readNextEntry();
				x = reader.readDoubleTag();
				y = reader.readDoubleTag();
				z = reader.readDoubleTag();
				((AbstractFireball) holder).setDirection(x, y ,z);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_EXPLOSION_POWER, (holder, reader) -> {
			if (holder instanceof AbstractFireball) {
				((AbstractFireball) holder).setExplosionPower(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_POWER, (holder, reader) -> {
			if (holder instanceof AbstractFireball) {
				double x;
				double y;
				double z;
				reader.readNextEntry();
				x = reader.readDoubleTag();
				y = reader.readDoubleTag();
				z = reader.readDoubleTag();
				((AbstractFireball) holder).setSpeed(x, y ,z);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LIFE, (holder, reader) -> {
			if (holder instanceof AbstractArrow) {
				((AbstractFireball) holder).setLifeTime(reader.readShortTag());
			} else reader.skipTag();
		});
	}
	
	private int lifeTime;
	private int explosionPower;
	private float explosionRadius;
	private boolean incendiary;
	private Vector direction;
	private Vector speed;
	
	public CoreAbstractFireball(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
		this.direction = new Vector();
		this.speed = new Vector();
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public float getExplosionRadius() {
		return explosionRadius;
	}

	@Override
	public void setExplosionRadius(float radius) {
		this.explosionRadius = radius;
	}
	
	@Override
	public boolean isIncendiary() {
		return incendiary;
	}
	
	@Override
	public void setIncendiary(boolean incendiary) {
		this.incendiary = incendiary;
	}

	@Override
	public Vector getDirection() {
		return direction;
	}

	@Override
	public void setDirection(Vector direction) {
		this.direction.set(direction);
	}
	
	@Override
	public void setDirection(double x, double y, double z) {
		this.direction.set(x, y, z);
	}

	@Override
	public void setExplosionPower(int power) {
		this.explosionPower = power;
	}
	
	@Override
	public int getExplosionPower() {
		return explosionPower;
	}

	@Override
	public Vector getSpeed() {
		return speed;
	}
	
	@Override
	public void setSpeed(Vector speed) {
		this.speed.set(speed);
	}
	
	@Override
	public void setSpeed(double x, double y, double z) {
		this.speed.set(x, y, z);
	}

	@Override
	public void setLifeTime(int ticks) {
		this.lifeTime = ticks;
	}
	
	@Override
	public int getLifeTime() {
		return lifeTime;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeListTag(NBT_DIRECTION, TagType.DOUBLE, 3);
		Vector vec = getDirection();
		writer.writeDoubleTag(null, vec.getX());
		writer.writeDoubleTag(null, vec.getY());
		writer.writeDoubleTag(null, vec.getZ());
		writer.writeIntTag(NBT_EXPLOSION_POWER, getExplosionPower());
		writer.writeListTag(NBT_POWER, TagType.DOUBLE, 3);
		vec = getSpeed();
		writer.writeDoubleTag(null, vec.getX());
		writer.writeDoubleTag(null, vec.getY());
		writer.writeDoubleTag(null, vec.getZ());
		writer.writeShortTag(NBT_LIFE, getLifeTime());
	}

}
