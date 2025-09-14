package de.atlasmc.core.node.entity;

import java.util.UUID;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Item;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.inventory.ItemStack;

public class CoreItem extends CoreEntity implements Item {

	protected static final MetaDataField<ItemStack>
	META_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	private short health = 5;
	private short age = 6000;
	private short pickupDelay;
	private UUID owner;
	private UUID thrower;
	
	public CoreItem(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ITEM);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_ITEM);
	}

	@Override
	public void setItem(ItemStack item) {
		MetaData<ItemStack> data = metaContainer.get(META_ITEM);
		data.setData(item);
		data.setChanged(true);
	}

	@Override
	public int getPickupDelay() {
		return pickupDelay;
	}

	@Override
	public void setPickupDelay(int delay) {
		this.pickupDelay = (short) delay;
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public void setOwner(UUID uuid) {
		this.owner = uuid;
	}

	@Override
	public UUID getThrower() {
		return thrower;
	}

	@Override
	public void setThrower(UUID uuid) {
		this.thrower = uuid;
	}

	@Override
	public void setLifeTime(int ticks) {
		this.age = (short) ticks;
	}

	@Override
	public int getLifeTime() {
		return age;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = (short) health;
	}

}
