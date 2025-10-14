package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Panda extends Animal {
	
	public static final NBTSerializationHandler<Panda>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Panda.class)
					.include(Animal.NBT_HANDLER)
					.enumStringField("HiddenGene", Panda::getHiddenGene, Panda::setHiddenGene, Gene.class, Gene.NORMAL)
					.enumStringField("MainGene", Panda::getMainGene, Panda::setMainGene, Gene.class, Gene.NORMAL)
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
	
	public static enum Gene implements IDHolder, EnumName {
		NORMAL(false),
		LAZY(false),
		WORRIED(false),
		PLAYFUL(false),
		BROWN(true),
		WEAK(true),
		AGGRESSIVE(false);
		
		private final boolean receccive;
		private final String name;
		
		private Gene(boolean receccive) {
			this.receccive = receccive;
			this.name = name().toLowerCase();
		}
		
		public boolean isRececcive() {
			return receccive;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
