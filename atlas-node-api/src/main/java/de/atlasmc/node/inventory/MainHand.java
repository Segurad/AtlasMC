package de.atlasmc.node.inventory;

import de.atlasmc.IDHolder;

public enum MainHand implements IDHolder {
	
	LEFT,
	RIGHT;
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public static MainHand getByID(int id) {
		return id == 0 ? LEFT : RIGHT;
	}

}
