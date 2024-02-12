package de.atlasmc.entity;

import java.util.List;

public interface Boat extends Vehicle {
	
	public BoatType getBoatType();
	
	public void setBoatType(BoatType type);
	
	public boolean isLeftPaddleTurning();
	
	public void setLeftPaddleTurning(boolean turning);
	
	public boolean isRightPaddleTurning();
	
	public void setRightPaddleTurning(boolean turning);
	
	public int getSplashTimer();
	
	public static enum BoatType {
		
		OAK,
		SPRUCE,
		BIRCH,
		JUNGLE,
		ACACIA,
		DARK_OAK;
	
		private static List<BoatType> VALUES;
		
		private final String name;
		
		private BoatType() {
			name = name().toLowerCase();
		}
		
		public int getID() {
			return ordinal();
		}

		public static BoatType getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<BoatType> getValues() {
			if (VALUES == null)
				synchronized (BoatType.class) {
					if (VALUES == null)
						VALUES = List.of(values());
				}
			return VALUES;
		}

		public static BoatType getByNameID(String nameID) {
			for (BoatType type : getValues()) {
				if (type.getNameID().equals(nameID));
			}
			return null;
		}

		public String getNameID() {
			return name;
		}
		
	}

}
