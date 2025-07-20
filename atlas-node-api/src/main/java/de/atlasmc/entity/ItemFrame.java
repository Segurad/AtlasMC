package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemFrame extends Hanging {
	
	public static final NBTSerializationHandler<ItemFrame>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemFrame.class)
					.include(Hanging.NBT_HANDLER)
					.boolField("Fixed", ItemFrame::isFixed, ItemFrame::setFixed)
					.boolField("Invisible", ItemFrame::isInvisible, ItemFrame::setInvisible)
					.typeComponentField("Item", ItemFrame::getItem, ItemFrame::setItemStack, ItemStack.NBT_HANDLER)
					.floatField("ItemDropChance", ItemFrame::getItemDropChance, ItemFrame::setItemDropChance, 1)
					.enumByteField("ItemRotation", ItemFrame::getRotation, ItemFrame::setRotation, Rotation::getByID, Rotation::getID, Rotation.NONE)
					.build();

	ItemStack getItem();
	
	void setItemStack(ItemStack item);
	
	void setItemStack(ItemStack item, boolean playSound);
	
	Rotation getRotation();
	
	void setRotation(Rotation rotation);
	
	void setFixed(boolean fixed);
	
	/**
	 * Returns whether or not this {@link ItemFrame} will stay at its position when the block it is on gets removed
	 * @return fixed
	 */
	boolean isFixed();

	void setItemDropChance(float chance);
	
	float getItemDropChance();
	
	@Override
	default NBTSerializationHandler<? extends ItemFrame> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Rotation implements EnumID, EnumValueCache {
		NONE,
		CLOCKWISE_45,
		CLOCKWISE_90,
		CLOCKWISE_135,
		FLIPPED,
		COUTNER_CLOCKWISE_135,
		COUNTER_CLOCKWISE_90,
		COUNTER_CLOCKWISE_45;
		
		public static List<Rotation> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Rotation getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Rotation> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}

	}
	
}
