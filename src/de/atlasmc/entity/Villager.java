package de.atlasmc.entity;

public interface Villager extends AbstractVillager {
	
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
			return values()[id];
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
		
		public int getID() {
			return ordinal();
		}
		
		public static VillagerProfession getByID(int id) {
			return values()[id];
		}
	}

}
