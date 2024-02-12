package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

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
	 * This value does not reset over time like {@link Breedable#isInLove()}
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
	
	public static enum VillagerType {
		DESERT("minecraft:desert"),
		JUNGLE("minecraft:jungle"),
		PLAINS("minecraft:plains"),
		SAVANNA("minecraft:savanna"),
		SNOW("minecraft:snow"),
		SWAMP("minecraft:swamp"),
		TAIGA("minecraft:taiga");
		
		private final String nameID;
		
		private VillagerType(String nameID) {
			this.nameID = nameID;
		}
		
		public String getNameID() {
			return nameID;
		}
		
		private static List<VillagerType> VALUES;
		
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

		public static VillagerType getByNameID(String nameID) {
			for (VillagerType type : getValues()) {
				if (type.getNameID().equals(nameID))
					return type;
			}
			return null;
		}
		
	}
	
	public static enum VillagerProfession {
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
		
		private final String nameID;
		
		private VillagerProfession(String nameID) {
			this.nameID = nameID;
		}
		
		public String getNameID() {
			return nameID;
		}
		
		private static List<VillagerProfession> VALUES;
		
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

		public static VillagerProfession getByNameID(String nameID) {
			for (VillagerProfession prof : getValues()) {
				if (prof.getNameID().equals(nameID))
					return prof;
			}
			return null;
		}
		
	}
	
}
