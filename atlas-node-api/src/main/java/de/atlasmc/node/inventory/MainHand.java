package de.atlasmc.node.inventory;

public enum MainHand {
	
	LEFT,
	RIGHT;
	
	public int getID() {
		return ordinal();
	}
	
	public static MainHand getByID(int id) {
		return id == 0 ? LEFT : RIGHT;
	}

}
