package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLACE_RECIPE)
public class PacketInPlaceRecipe extends AbstractPacket implements PacketPlayIn {
	
	private int windowID;
	private String recipe;
	private boolean makeAll;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public String getRecipe() {
		return recipe;
	}
	
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	
	public boolean getMakeAll() {
		return makeAll;
	}
	
	public void setMakeAll(boolean makeAll) {
		this.makeAll = makeAll;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLACE_RECIPE;
	}

}
