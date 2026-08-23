package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public class ItemFrameMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ItemFrameMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(ItemFrameMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("Fixed", ItemFrameMetaComponent::isFixed, ItemFrameMetaComponent::setFixed)
					.codec("Item", ItemFrameMetaComponent::getItem, ItemFrameMetaComponent::setItemStack, ItemStack.NBT_CODEC)
					.floatField("ItemDropChance", ItemFrameMetaComponent::getItemDropChance, ItemFrameMetaComponent::setItemDropChance, 1)
					.codec("ItemRotation", ItemFrameMetaComponent::getRotation, ItemFrameMetaComponent::setRotation, EnumUtil.enumByteNBTCodec(Rotation.class), Rotation.NONE)
					.build();

	public static final MetaDataField<ItemStack>
	META_FRAME_ITEM = new MetaDataField<>(9, null, EntityMetaTypes.SLOT);
	public static final MetaDataField<Integer>
	META_FRAME_ROTATION = new MetaDataField<>(10, Rotation.NONE.getID(), EntityMetaTypes.VAR_INT);
	
	private boolean fixed;
	private float dropChance = 1.0f;
	
	public ItemFrameMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_FRAME_ITEM);
		container.set(META_FRAME_ROTATION);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}

	public ItemStack getItem() {
		return getHolder().getMetaContainer().getData(META_FRAME_ITEM);
	}

	public void setItemStack(ItemStack item) {
		getHolder().getMetaContainer().setData(META_FRAME_ITEM, item);
	}

	public void setItemStack(ItemStack item, boolean playSound) {
		setItemStack(item);
		if (!playSound)
			return;
		Sound sound = item != null ? EnumSound.ENTITY_ITEM_FRAME_ADD_ITEM : EnumSound.ENTITY_ITEM_FRAME_REMOVE_ITEM;
		getHolder().causeSound(sound, SoundCategory.MASTER, 1.0f, 1.0f, Sound.DEFAULT_SEED); // TODO random seeding
	}

	public Rotation getRotation() {
		return EnumUtil.getByID(Rotation.class, getHolder().getMetaContainer().getData(META_FRAME_ROTATION));
	}

	public void setRotation(Rotation rotation) {
		getHolder().getMetaContainer().setData(META_FRAME_ROTATION, rotation.getID());
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	/**
	 * Returns whether or not this {@link ItemFrame} will stay at its position when the block it is on gets removed
	 * @return fixed
	 */
	public boolean isFixed() {
		return fixed;
	}

	public void setItemDropChance(float chance) {
		this.dropChance = chance;
	}

	public float getItemDropChance() {
		return dropChance;
	}
	
	@Override
	public NBTCodec<? extends ItemFrameMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Rotation implements IDHolder {
		
		NONE,
		CLOCKWISE_45,
		CLOCKWISE_90,
		CLOCKWISE_135,
		FLIPPED,
		COUTNER_CLOCKWISE_135,
		COUNTER_CLOCKWISE_90,
		COUNTER_CLOCKWISE_45;
		
		@Override
		public int getID() {
			return ordinal();
		}
	
	}
	
}
