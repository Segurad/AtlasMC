package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.ProjectileSource;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FireworkRocket;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.meta.FireworkMeta;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreFireworkRocket extends CoreAbstractProjectile implements FireworkRocket {
	
	protected static final MetaDataField<ItemStack>
	META_FIREWORK_INFO = new MetaDataField<>(CoreAbstractProjectile.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Integer>
	META_SHOOTER_ID = new MetaDataField<>(CoreAbstractProjectile.LAST_META_INDEX+2, null, MetaDataType.OPT_INT);
	protected static final MetaDataField<Boolean>
	META_SHOT_AT_ANGLE = new MetaDataField<>(CoreAbstractProjectile.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractProjectile.LAST_META_INDEX+3;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_LIFE = "Life",
	NBT_LIFE_TIME = "LifeTime",
	NBT_FIREWORK_ITEM = "FireworkItem";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractProjectile.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_LIFE, (holder, reader) -> {
			if (holder instanceof FireworkRocket) {
				((FireworkRocket) holder).setLifeTime(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LIFE_TIME, (holder, reader) -> {
			if (holder instanceof FireworkRocket) {
				((FireworkRocket) holder).setMaxLifeTime(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FIREWORK_ITEM, (holder, reader) -> {
			if (!(holder instanceof FireworkRocket)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			Material mat = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			((FireworkRocket) holder).setFirework(item);
		});
	}
	
	private int lifeTime;
	private int maxLifeTime;
	
	public CoreFireworkRocket(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FIREWORK_INFO);
		metaContainer.set(META_SHOOTER_ID);
		metaContainer.set(META_SHOT_AT_ANGLE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.FIREWORK_ROCKET;
	}

	@Override
	public FireworkMeta getFireworkMeta() {
		ItemStack item = getFirework();
		if (item == null)
			return null;
		return (FireworkMeta) item.getItemMeta();
	}

	@Override
	public boolean isShotAtAngle() {
		return metaContainer.getData(META_SHOT_AT_ANGLE);
	}

	@Override
	public void detonate() {
		// TODO detonate firework
		
	}

	@Override
	public void setFireworkMeta(FireworkMeta meta) {
		if (meta == null) {
			metaContainer.get(META_FIREWORK_INFO).setData(null);
			return;
		}
		MetaData<ItemStack> data = metaContainer.get(META_FIREWORK_INFO);
		ItemStack item = data.getData();
		if (item == null)
			item = new ItemStack(Material.FIREWORK_ROCKET);
		item.setItemMeta(meta);
		metaContainer.get(META_FIREWORK_INFO).setData(item);
		data.setChanged(true);
	}

	@Override
	public void setShotAtAngle(boolean shotAtAngle) {
		metaContainer.get(META_SHOT_AT_ANGLE).setData(shotAtAngle);
	}

	@Override
	public ItemStack getFirework() {
		return metaContainer.getData(META_FIREWORK_INFO);
	}

	@Override
	public void setFirework(ItemStack firework) {
		if (firework != null && firework.getType() != Material.FIREWORK_ROCKET)
			throw new IllegalArgumentException("Item must have the type FIREWORK_ROCKET");
		metaContainer.get(META_FIREWORK_INFO).setData(firework);
	}
	
	@Override
	public void setShooter(ProjectileSource source) {
		super.setShooter(source);
		if (!(source instanceof Entity)) {
			metaContainer.get(META_SHOOTER_ID).setData(null);
			return;
		}
		Entity entity = (Entity) source;
		metaContainer.get(META_SHOOTER_ID).setData(entity.getID());
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
	public void setMaxLifeTime(int ticks) {
		this.maxLifeTime = ticks;
	}

	@Override
	public int getMaxLifeTime() {
		return maxLifeTime;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_LIFE, getLifeTime());
		writer.writeIntTag(NBT_LIFE_TIME, getMaxLifeTime());
		if (getFirework() != null) {
			writer.writeCompoundTag(NBT_FIREWORK_ITEM);
			getFirework().toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

}
