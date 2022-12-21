package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.recipe.Recipe;

@DefaultPacketID(PacketPlay.OUT_DECLARE_RECIPES)
public class PacketOutDeclareRecipes extends AbstractPacket implements PacketPlayOut {
	
	private List<Recipe> recipes;
	
	public List<Recipe> getRecipes() {
		return recipes;
	}
	
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DECLARE_RECIPES;
	}

}
