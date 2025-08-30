package de.atlascore.entity;

import de.atlasmc.ProjectileSource;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FireworkRocket;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.FireworksComponent;

public class CoreFireworkRocket extends CoreAbstractProjectile implements FireworkRocket {
	
	protected static final MetaDataField<ItemStack>
	META_FIREWORK_INFO = new MetaDataField<>(CoreAbstractProjectile.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Integer>
	META_SHOOTER_ID = new MetaDataField<>(CoreAbstractProjectile.LAST_META_INDEX+2, null, MetaDataType.OPT_VAR_INT);
	protected static final MetaDataField<Boolean>
	META_SHOT_AT_ANGLE = new MetaDataField<>(CoreAbstractProjectile.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractProjectile.LAST_META_INDEX+3;
	
	private int lifeTime;
	private int maxLifeTime;
	
	public CoreFireworkRocket(EntityType type) {
		super(type);
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
	public FireworksComponent getFireworkMeta() {
		ItemStack item = getFirework();
		if (item == null)
			return null;
		return item.getComponent(ComponentType.FIREWORKS.get());
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
	public void setFireworkMeta(FireworksComponent component) {
		if (component == null) {
			metaContainer.get(META_FIREWORK_INFO).setData(null);
			return;
		}
		MetaData<ItemStack> data = metaContainer.get(META_FIREWORK_INFO);
		ItemStack item = data.getData();
		if (item == null)
			item = new ItemStack(ItemType.FIREWORK_ROCKET.get());
		item.setComponent(component);
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

}
