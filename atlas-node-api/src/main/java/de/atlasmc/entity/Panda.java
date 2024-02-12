package de.atlasmc.entity;

import java.util.List;

public interface Panda extends Animal {
	
	public int getBreedTimer();
	
	public void setBreedTimer(int time);
	
	public int getSneezeTimer();
	
	public void setSneezeTimer(int time);
	
	public int getEatTimer();
	
	public void setEatTimer(int time);
	
	public Gene getMainGene();
	
	public void setMainGene(Gene gene);
	
	public Gene getHiddenGene();
	
	public void setHiddenGene(Gene gene);
	
	public boolean isSneezing();
	
	public void setSneezing(boolean sneezing);
	
	public boolean isRolling();
	
	public void setRolling(boolean rolling);
	
	public boolean isSitting();
	
	public void setSitting(boolean sitting);
	
	public boolean isOnBack();
	
	public void setOnBack(boolean onback);
	
	public static enum Gene {
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

		public static Gene getByNameID(String name) {
			for (Gene gene : getValues()) {
				if (gene.name.equals(name))
					return gene;
			}
			return null;
		}
		
		public String getNameID() {
			return name;
		}
		
	}

}
