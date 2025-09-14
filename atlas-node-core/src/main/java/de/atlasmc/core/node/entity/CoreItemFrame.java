package de.atlasmc.core.node.entity;

import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ItemFrame;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;

public class CoreItemFrame extends CoreHanging implements ItemFrame {

	protected static final MetaDataField<ItemStack>
	META_FRAME_ITEM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Integer>
	META_FRAME_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	private boolean fixed;
	private float dropChance = 1.0f;
	
	public CoreItemFrame(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_FRAME_ITEM));
		metaContainer.set(new MetaData<>(META_FRAME_ROTATION));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_FRAME_ITEM);
	}

	@Override
	public void setItemStack(ItemStack item) {
		MetaData<ItemStack> data =metaContainer.get(META_FRAME_ITEM);
		data.setData(item);
		data.setChanged(true);
	}

	@Override
	public void setItemStack(ItemStack item, boolean playSound) {
		setItemStack(item);
		if (!playSound)
			return;
		Sound sound = item != null ? EnumSound.ENTITY_ITEM_FRAME_ADD_ITEM : EnumSound.ENTITY_ITEM_FRAME_REMOVE_ITEM;
		causeSound(sound, SoundCategory.MASTER, 1.0f, 1.0f, Sound.DEFAULT_SEED); // TODO random seeding
	}

	@Override
	public Rotation getRotation() {
		return Rotation.getByID(metaContainer.getData(META_FRAME_ROTATION));
	}

	@Override
	public void setRotation(Rotation rotation) {
		metaContainer.get(META_FRAME_ROTATION).setData(rotation.getID());
	}

	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

	@Override
	public void setItemDropChance(float chance) {
		this.dropChance = chance;
	}

	@Override
	public float getItemDropChance() {
		return dropChance;
	}

}
