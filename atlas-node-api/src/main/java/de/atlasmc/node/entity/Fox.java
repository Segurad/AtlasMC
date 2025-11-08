package de.atlasmc.node.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Fox extends Animal {
	
	public static final NBTCodec<Fox>
	NBT_HANDLER = NBTCodec
					.builder(Fox.class)
					.include(Animal.NBT_CODEC)
					.boolField("Crouching", Fox::isCrouching, Fox::setCrouching, false)
					.boolField("Sitting", Fox::isSitting, Fox::setSitting, false)
					.boolField("Sleeping", Fox::isSleeping, Fox::setSleeping, false)
					.codecList("Trusted", Fox::hasTrusted, Fox::getTrusted, NBTCodecs.UUID_CODEC, true)
					.codec("Type", Fox::getFoxType, Fox::setFoxType, EnumUtil.enumStringNBTCodec(Type.class), Type.RED)
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
	default NBTCodec<? extends Fox> getNBTCodec() {
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
