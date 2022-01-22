package de.atlasmc.entity;

import java.util.List;

public interface Villager extends AbstractVillager {
	
	public VillagerType getVillagerType();
	
	public void setVillagerType(VillagerType type);
	
	public VillagerProfession getVillagerProfession();
	
	public void setVillagerProfession(VillagerProfession profession);
	
	public int getLevel();
	
	public void setLevel(int level);
	
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
		DESERT,
		JUNGLE,
		PLAINS,
		SAVANNA,
		SNOW,
		SWAMP,
		TAIGA;
		
		public int getID() {
			return ordinal();
		}
		
		public static VillagerType getByID(int id) {
			switch (id) {
			case 0:
				return DESERT;
			case 1:
				return JUNGLE;
			case 2:
				return PLAINS;
			case 3:
				return SAVANNA;
			case 4:
				return SWAMP;
			case 5:
				return TAIGA;
			default:
				return PLAINS;
			}
		}
	}
	
	public static enum VillagerProfession {
		NONE,
		ARMORER,
		BUTCHER,
		CARTOGRAPHER,
		CLERIC,
		FARMER,
		FISHERMAN,
		FLETCHER,
		LETHERWORKER,
		LIBRARIAN,
		MASON,
		NITWIT,
		SHEPHERD,
		TOOLSMITH,
		WEAPONSMITH;
		
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
		
	}

}
