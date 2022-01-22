package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Item;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.world.World;

public class CoreItem extends CoreEntity implements Item {

	protected static final MetaDataField<ItemStack>
	META_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	private int pickupDelay;
	private UUID owner;
	private UUID thrower;
	
	public CoreItem(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
	public ItemStack getItemStack() {
		return metaContainer.getData(META_ITEM);
	}

	@Override
	public void setItemStack(ItemStack item) {
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
		this.pickupDelay = delay;
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

}
