package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractVillager extends Merchant {
	
	public static final NBTSerializationHandler<AbstractVillager>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractVillager.class)
					.include(Merchant.NBT_HANDLER)
					// Gossip
					.typeCompoundField("VillagerData", AbstractVillager::getVillagerDataUnsafe, AbstractVillager::setVillagerData, VillagerData.NBT_HANDLER)
					.intField("Xp", AbstractVillager::getXp, AbstractVillager::setXp)
					.build();
	
	/**
	 * Returns the amount of xp this villager has gained through trading
	 * @return
	 */
	int getXp();
	
	/**
	 * Sets the amount of xp this villager has gained through trading
	 * @param xp
	 */
	void setXp(int xp);
	
	/**
	 * Adds a amount to xp this villager has gained through trading
	 * @param xp
	 */
	void addXp(int xp);
	
	@UnsafeAPI
	VillagerData getVillagerDataUnsafe();
	
	VillagerData getVillagerData();
	
	void setVillagerData(VillagerData data);
	
	public static class VillagerData implements NBTSerializable, Cloneable {
		
		public static final NBTSerializationHandler<VillagerData>
		NBT_HANDLER = NBTSerializationHandler
						.builder(VillagerData.class)
						.defaultConstructor(VillagerData::new)
						.intField("level", VillagerData::getLevel, VillagerData::setLevel, 1)
						.enumStringField("profession", VillagerData::getProfession, VillagerData::setProfession, VillagerProfession::getByName, VillagerProfession.NONE)
						.enumStringField("type", VillagerData::getType, VillagerData::setType, VillagerType::getByName, VillagerType.PLAINS)
						.build();
		
		private VillagerType type;
		private VillagerProfession profession;
		private int level;
		
		public VillagerData() {
			this(VillagerType.PLAINS, VillagerProfession.NONE, 1);
		}
		
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
		
		@Override
		public NBTSerializationHandler<? extends VillagerData> getNBTHandler() {
			return NBT_HANDLER;
		}
		
		@Override
		public VillagerData clone() {
			try {
				return (VillagerData) super.clone();
			} catch (CloneNotSupportedException e) {}
			return null;
		}

		public void set(VillagerData data) {
			this.level = data.level;
			this.type = data.type;
			this.profession = data.profession;
		}
		
	}
	
	public static enum VillagerType implements AtlasEnum {
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
	
	public static enum VillagerProfession implements AtlasEnum {
		
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
