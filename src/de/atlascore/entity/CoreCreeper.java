package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Creeper;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreCreeper extends CoreMob implements Creeper {
	
	protected static final MetaDataField<Integer>
	META_CREEPER_STATE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, -1, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_IS_CHARGED = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_IGNITED = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;

	protected static final CharKey
	NBT_EXPLOSION_RADIUS = CharKey.of("ExplosionRadius"),
	NBT_FUSE = CharKey.of("Fuse"),
	NBT_IGNITED = CharKey.of("Ignited"),
	NBT_POWERED = CharKey.of("Powered");
	
	static {
		NBT_FIELDS.setField(NBT_EXPLOSION_RADIUS, (holder, reader) -> {
			if (holder instanceof Creeper) {
				((Creeper) holder).setExplosionRadius(reader.readByteTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FUSE, (holder, reader) -> {
			if (holder instanceof Creeper) {
				((Creeper) holder).setFuseTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_IGNITED, (holder, reader) -> {
			if (holder instanceof Creeper) {
				((Creeper) holder).setIgnited(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_POWERED, (holder, reader) -> {
			if (holder instanceof Creeper) {
				((Creeper) holder).setChared(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private int fuzeTime = -1;
	private int radius = 3;
	
	public CoreCreeper(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CREEPER_STATE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getFuseTime() {
		return fuzeTime;
	}

	@Override
	public boolean isChared() {
		return metaContainer.getData(META_IS_CHARGED);
	}

	@Override
	public boolean isIgnited() {
		return metaContainer.getData(META_IS_IGNITED);
	}

	@Override
	public void setFuseTime(int fuze) {
		this.fuzeTime = fuze;
	}

	@Override
	public void setChared(boolean charged) {
		metaContainer.get(META_IS_CHARGED).setData(charged);
	}

	@Override
	public void setIgnited(boolean ignited) {
		metaContainer.get(META_IS_IGNITED).setData(ignited);
	}

	@Override
	public boolean isFusing() {
		return metaContainer.getData(META_CREEPER_STATE) == 1;
	}

	@Override
	public void setFusing(boolean fuzing) {
		metaContainer.get(META_CREEPER_STATE).setData(fuzing ? 1 : -1);
	}

	@Override
	public void setExplosionRadius(int radius) {
		if (radius > 127)
			throw new IllegalArgumentException("Radius can not be higher than 127: " + radius);
		if (radius < 0)
			radius = 0;
		this.radius = radius;	
	}

	@Override
	public int getExplosionRadius() {
		return radius;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_EXPLOSION_RADIUS, getExplosionRadius());
		writer.writeShortTag(NBT_FUSE, getFuseTime());
		writer.writeByteTag(NBT_IGNITED, isIgnited());
		writer.writeByteTag(NBT_POWERED, isChared());
	}
	
}
