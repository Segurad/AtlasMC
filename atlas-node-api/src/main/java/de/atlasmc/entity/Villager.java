package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Villager extends AbstractVillager {
	
	public VillagerType getVillagerType();
	
	public void setVillagerType(VillagerType type);
	
	public VillagerProfession getVillagerProfession();
	
	public void setVillagerProfession(VillagerProfession profession);
	
	public int getLevel();
	
	public void setLevel(int level);
	
	public List<ItemStack> getPocketContents();
	
	public void addPocketItem(ItemStack item);
	
	public void setPocketItems(List<ItemStack> pocket);
	
	public boolean hasPocketItems();
	
	/**
	 * Returns the amount of xp this villager has gained through trading
	 * @return
	 */
	public int getXp();
	
	/**
	 * Sets the amount of xp this villager has gained through trading
	 * @param xp
	 */
	public void setXp(int xp);
	
	/**
	 * Adds a amount to xp this villager has gained through trading
	 * @param xp
	 */
	public void addXp(int xp);
	
	/**
	 * Returns whether or not this Villager is ready for breeding.<br>
	 * @return true if ready
	 */
	public boolean isWilling();
	
	/**
	 * Sets whether or not this Villager is ready for breeding.<br>
	 * This value does not reset over time like {@link Breedable#getInLove()}
	 * @param willing
	 */
	public void setWilling(boolean willing);
	
	public static class VillagerData {
		
		private VillagerType type;
		private VillagerProfession profession;
		private int level;
		
		public VillagerData(VillagerType type, VillagerProfession profession, int level) {
			this.type = type;
			this.profession = profession;
			this.level = level;
		}
		
		public VillagerType getType() {
			return type;
		}
		
		public int getLevel() {
			return level;
		}
		
		public VillagerProfession getProfession() {
			return profession;
		}
		
		public void setType(VillagerType type) {
			this.type = type;
		}
		
		public void setLevel(int level) {
			this.level = level;
		}
		
		public void setProfession(VillagerProfession profession) {
			this.profession = profession;
		}
		
	}
	
	public static enum VillagerType implements EnumName, EnumID, EnumValueCache {
		DESERT("minecraft:desert"),
		JUNGLE("minecraft:jungle"),
		PLAINS("minecraft:plains"),
		SAVANNA("minecraft:savanna"),
		SNOW("minecraft:snow"),
		SWAMP("minecraft:swamp"),
		TAIGA("minecraft:taiga");
		
		private static List<VillagerType> VALUES;
		
		private final String name;
		
		private VillagerType(String name) {
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static VillagerType getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<VillagerType> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

		public static VillagerType getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException(name);
			List<VillagerType> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				VillagerType type = values.get(i);
				if (type.name.equals(name))
					return type;
			}
			return null;
		}
		
	}
	
	public static enum VillagerProfession implements EnumName, EnumID, EnumValueCache {
		
		NONE("minecraft:none"),
		ARMORER("minecraft:armorer"),
		BUTCHER("minecraft:butcher"),
		CARTOGRAPHER("minecraft:cartographer"),
		CLERIC("minecraft:cleric"),
		FARMER("minecraft:farmer"),
		FISHERMAN("minecraft:fisherman"),
		FLETCHER("minecraft:fletcher"),
		LETHERWORKER("minecraft:leatherworker"),
		LIBRARIAN("minecraft:librarian"),
		MASON("minecraft:mason"),
		NITWIT("minecraft:nitwit"),
		SHEPHERD("minecraft:shepherd"),
		TOOLSMITH("minecraft:toolsmith"),
		WEAPONSMITH("minecraft:weaponsmith");
		
		private static List<VillagerProfession> VALUES;
		
		private final String name;
		
		private VillagerProfession(String name) {
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static VillagerProfession getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<VillagerProfession> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

		public static VillagerProfession getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<VillagerProfession> professions = getValues();
			final int size = professions.size();
			for (int i = 0; i < size; i++) {
				VillagerProfession profession = professions.get(i);
				if (profession.name.equals(name))
					return profession;
			}
			return null;
		}
		
	}
	
}
