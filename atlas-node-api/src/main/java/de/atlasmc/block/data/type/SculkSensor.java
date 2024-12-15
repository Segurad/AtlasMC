package de.atlasmc.block.data.type;

import java.util.List;

import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.block.data.Waterlogged;

public interface SculkSensor extends AnaloguePowerable, Waterlogged {

	void setPhase(Phase phase);
	
	Phase getPhase();
	
	SculkSensor clone();
	
	public static enum Phase  {
		
		INACTIVE,
		ACTIVE,
		COOLDOWN;
		
		private static List<Phase> VALUES;
		
		private final String name;
		
		private Phase() {
			this.name = name().toLowerCase();
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static Phase getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Phase> getValues() {
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
		
		public String getName() {
			return name;
		}
		
		public static Phase getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			for (Phase phase : getValues()) {
				if (phase.getName().equals(name))
					return phase;
			}
			throw new IllegalArgumentException("No value with found with name: " + name);
		}
		
	}
	
}
