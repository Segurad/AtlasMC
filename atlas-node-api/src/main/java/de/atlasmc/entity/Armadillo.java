package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Armadillo extends Animal {
	
	int getScuteTime();
	
	void setScuteTime(int time);
	
	ArmadilloState getState();
	
	void setState(ArmadilloState state);
	
	public static enum ArmadilloState implements EnumName, EnumValueCache, EnumID {
		
		IDLE,
		ROLLING,
		SCARED,
		UNROLLING;
		
		private static List<ArmadilloState> VALUES;
		
		private String name;
		
		private ArmadilloState() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static ArmadilloState getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<ArmadilloState> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				ArmadilloState value = values.get(i);
				if (value.name.equals(name)) 
					return value;
			}
			return null;
		}
		
		public static ArmadilloState getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<ArmadilloState> getValues() {
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
		
	}

}
