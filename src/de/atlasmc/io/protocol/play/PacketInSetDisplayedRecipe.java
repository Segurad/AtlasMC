package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_SET_DISPLAYED_RECIPE)
public class PacketInSetDisplayedRecipe extends AbstractPacket implements PacketPlayIn {
	
	private String recipeID;
	
	public String getRecipeID() {
		return recipeID;
	}
	
	public void setRecipeID(String recipeID) {
		this.recipeID = recipeID;
	}
	
	@Override
	public int getDefaultID() {
		return IN_SET_DISPLAYED_RECIPE;
	}

}
