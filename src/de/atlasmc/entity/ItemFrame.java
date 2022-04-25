package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.block.BlockFace;
import de.atlasmc.inventory.ItemStack;

public interface ItemFrame extends Entity {

	public ItemStack getItem();
	
	public void setItemStack(ItemStack item);
	
	public void setItemStack(ItemStack item, boolean playSound);
	
	public Rotation getRotation();
	
	public void setRotation(Rotation rotation);
	
	public void setFixed(boolean fixed);
	
	/**
	 * Returns whether or not this {@link ItemFrame} will stay at its position when the block it is on gets removed
	 * @return fixed
	 */
	public boolean isFixed();
	
	public BlockFace getOrientation();
	
	public void setOrientation(BlockFace orientation);

	public void setItemDropChance(float chance);
	
	public float getItemDropChance();
	
	public static enum Rotation {
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
