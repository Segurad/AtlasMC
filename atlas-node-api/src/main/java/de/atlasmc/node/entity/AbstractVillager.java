package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AbstractVillager extends Merchant {
	
	public static final NBTCodec<AbstractVillager>
	NBT_HANDLER = NBTCodec
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
		
		public static final NBTCodec<VillagerData>
		NBT_HANDLER = NBTCodec
						.builder(VillagerData.class)
						.defaultConstructor(VillagerData::new)
						.intField("level", VillagerData::getLevel, VillagerData::setLevel, 1)
						.enumStringField("profession", VillagerData::getProfession, VillagerData::setProfession, VillagerProfession.class, VillagerProfession.NONE)
						.enumStringField("type", VillagerData::getType, VillagerData::setType, VillagerType.class, VillagerType.PLAINS)
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
		public NBTCodec<? extends VillagerData> getNBTCodec() {
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
	
	public static enum VillagerType implements EnumName, IDHolder {
		
		DESERT("minecraft:desert"),
		JUNGLE("minecraft:jungle"),
		PLAINS("minecraft:plains"),
		SAVANNA("minecraft:savanna"),
		SNOW("minecraft:snow"),
		SWAMP("minecraft:swamp"),
		TAIGA("minecraft:taiga");
		
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
		
	}
	
	public static enum VillagerProfession implements EnumName, IDHolder {
		
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
		
	}
	
}
