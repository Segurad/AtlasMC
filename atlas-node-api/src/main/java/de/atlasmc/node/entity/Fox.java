package de.atlasmc.node.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.IDHolder;
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
					.enumStringField("Type", Fox::getFoxType, Fox::setFoxType, Type.class, Type.RED)
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

	public static enum Type implements EnumName, IDHolder {
		
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

		@Override
		public int getID() {
			return ordinal();
		}

	}
	
}
