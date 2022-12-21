package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CRAFT_RECIPE_RESPONSE)
public class PacketOutCraftRecipeResponse extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	private String recipe;
	
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
	
	@Override
	public int getDefaultID() {
		return OUT_CRAFT_RECIPE_RESPONSE;
	}

}
