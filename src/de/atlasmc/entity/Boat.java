package de.atlasmc.entity;

public interface Boat extends Vehicle {
	
	public int getTimeSinceLastHit();
	public int getForwardDirection();
	public float getDamageTaken();
	public BoatType getBoatType();
	public boolean isLeftPaddleTurning();
	public boolean isRightPaddleTurning();
	public int getSplashTimer();
	
	public static enum BoatType {
		
		OAK(0),
		SPRUCE(1),
		BIRCH(2),
		JUNGLE(3),
		ACACIA(4),
		DARK_OAK(5);
		
		private int id;
		private BoatType(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
	}

}
