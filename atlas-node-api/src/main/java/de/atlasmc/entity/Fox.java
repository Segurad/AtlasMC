package de.atlasmc.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Fox extends Animal {
	
	public static final NBTSerializationHandler<Fox>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Fox.class)
					.include(Animal.NBT_HANDLER)
					.boolField("Crouching", Fox::isCrouching, Fox::setCrouching, false)
					.boolField("Sitting", Fox::isSitting, Fox::setSitting, false)
					.boolField("Sleeping", Fox::isSleeping, Fox::setSleeping, false)
					.uuidList("Trusted", Fox::hasTrusted, Fox::getTrusted, true)
					.enumStringField("Type", Fox::getFoxType, Fox::setFoxType, Type::getByName, Type.RED)
					.build();
	
	Type getFoxType();
	
	void setFoxType(Type type);
	
	boolean isSitting();
	
	void setSitting(boolean sitting);
	
	boolean isInterested();
	
	void setInterested(boolean interested);
	
	boolean isPouncing();
	
	void setPouncing(boolean pouncing);
	
	boolean isSleeping();
	
	void setSleeping(boolean sleeping);
	
	boolean isFaceplanted();
	
	void setFaceplanted(boolean faceplanted);
	
	boolean isDefending();
	
	void setDefending(boolean defending);
	
	UUID getFirstTrusted();
	
	void setFirstTrusted(UUID uuid);
	
	UUID getSecondTrusted();
	
	void setSecondTrusted(UUID uuid);
	
	boolean isCrouching();
	
	void setCrouching(boolean crouching);

	void addTrusted(UUID trusted);
	
	boolean isTrusted(UUID trusted);

	List<UUID> getTrusted();
	
	boolean hasTrusted();
	
	/**
	 * Removes the UUID from the trusted set
	 * @param trusted
	 * @return true if UUID was present and removed
	 */
	boolean removeTrusted(UUID trusted);
	
	@Override
	default NBTSerializationHandler<? extends Fox> getNBTHandler() {
		return NBT_HANDLER;
	}

	public static enum Type implements EnumName, EnumID {
		RED,
		SNOW;
		
		private final String name;
		
		private Type() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
 
		public static Type getByID(int id) {
			switch (id) {
			case 0:
				return RED;
			case 1:
				return SNOW;
			default:
				return RED;
			}
		}

		@Override
		public int getID() {
			return ordinal();
		}

		public static Type getByName(String name) {
			switch (name) {
			case "red":
				return RED;
			case "snow":
				return SNOW;
			default:
				return null;
			}
		}
	}
	
}
