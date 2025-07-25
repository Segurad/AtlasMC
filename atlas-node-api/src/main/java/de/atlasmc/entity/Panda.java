package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Panda extends Animal {
	
	public static final NBTSerializationHandler<Panda>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Panda.class)
					.include(Animal.NBT_HANDLER)
					.enumStringField("HiddenGene", Panda::getHiddenGene, Panda::setHiddenGene, Gene::getByName, Gene.NORMAL)
					.enumStringField("MainGene", Panda::getMainGene, Panda::setMainGene, Gene::getByName, Gene.NORMAL)
					// non standard
					.boolField("IsSneezing", Panda::isSneezing, Panda::setSneezing, false)
					.boolField("IsRolling", Panda::isRolling, Panda::setRolling, false)
					.boolField("IsSitting", Panda::isSitting, Panda::setSitting, false)
					.boolField("IsOnBack", Panda::isOnBack, Panda::setOnBack, false)
					.build();
	
	int getBreedTimer();
	
	void setBreedTimer(int time);
	
	int getSneezeTimer();
	
	void setSneezeTimer(int time);
	
	int getEatTimer();
	
	void setEatTimer(int time);
	
	Gene getMainGene();
	
	void setMainGene(Gene gene);
	
	Gene getHiddenGene();
	
	void setHiddenGene(Gene gene);
	
	boolean isSneezing();
	
	void setSneezing(boolean sneezing);
	
	boolean isRolling();
	
	void setRolling(boolean rolling);
	
	boolean isSitting();
	
	void setSitting(boolean sitting);
	
	boolean isOnBack();
	
	void setOnBack(boolean onback);
	
	@Override
	default NBTSerializationHandler<? extends Panda> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Gene implements AtlasEnum {
		NORMAL(false),
		LAZY(false),
		WORRIED(false),
		PLAYFUL(false),
		BROWN(true),
		WEAK(true),
		AGGRESSIVE(false);
		
		private static List<Gene> VALUES;
		
		private final boolean receccive;
		private final String name;
		
		private Gene(boolean receccive) {
			this.receccive = receccive;
			this.name = name().toLowerCase();
		}
		
		public boolean isRececcive() {
			return receccive;
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static Gene getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Gene> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}

		public static Gene getByName(String name) {
			for (Gene gene : getValues()) {
				if (gene.name.equals(name))
					return gene;
			}
			return null;
		}
		
		public String getName() {
			return name;
		}
		
	}

}
