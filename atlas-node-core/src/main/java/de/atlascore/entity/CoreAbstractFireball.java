package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import org.joml.Vector3d;

import de.atlasmc.entity.AbstractFireball;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractFireball extends CoreAbstractProjectile implements AbstractFireball {

	protected static final NBTFieldContainer<CoreAbstractFireball> NBT_FIELDS;
	
	protected static final CharKey
	NBT_DIRECTION = CharKey.literal("direction"),
	NBT_EXPLOSION_POWER = CharKey.literal("ExplosionPower"),
	NBT_POWER = CharKey.literal("power"),
	NBT_LIFE = CharKey.literal("life");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAbstractProjectile.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_DIRECTION, (holder, reader) -> {
			double x;
			double y;
			double z;
			reader.readNextEntry();
			x = reader.readDoubleTag();
			y = reader.readDoubleTag();
			z = reader.readDoubleTag();
			holder.setDirection(x, y ,z);
		});
		NBT_FIELDS.setField(NBT_EXPLOSION_POWER, (holder, reader) -> {
			holder.setExplosionPower(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_POWER, (holder, reader) -> {
			double x;
			double y;
			double z;
			reader.readNextEntry();
			x = reader.readDoubleTag();
			y = reader.readDoubleTag();
			z = reader.readDoubleTag();
			holder.setSpeed(x, y ,z);
		});
		NBT_FIELDS.setField(NBT_LIFE, (holder, reader) -> {
			holder.setLifeTime(reader.readShortTag());
		});
	}
	
	private int lifeTime;
	private int explosionPower;
	private float explosionRadius;
	private boolean incendiary;
	private Vector3d direction;
	private Vector3d speed;
	
	public CoreAbstractFireball(EntityType type, UUID uuid) {
		super(type, uuid);
		this.direction = new Vector3d();
		this.speed = new Vector3d();
	}

	@Override
	protected NBTFieldContainer<CoreAbstractFireball> getFieldContainerRoot() {
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
	public Vector3d getDirection() {
		return direction;
	}

	@Override
	public void setDirection(Vector3d direction) {
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
	public Vector3d getSpeed() {
		return speed;
	}
	
	@Override
	public void setSpeed(Vector3d speed) {
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
		Vector3d vec = getDirection();
		writer.writeDoubleTag(null, vec.x);
		writer.writeDoubleTag(null, vec.y);
		writer.writeDoubleTag(null, vec.z);
		writer.writeIntTag(NBT_EXPLOSION_POWER, getExplosionPower());
		writer.writeListTag(NBT_POWER, TagType.DOUBLE, 3);
		vec = getSpeed();
		writer.writeDoubleTag(null, vec.x);
		writer.writeDoubleTag(null, vec.y);
		writer.writeDoubleTag(null, vec.z);
		writer.writeShortTag(NBT_LIFE, getLifeTime());
	}

}
