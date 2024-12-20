package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.recipe.Recipe;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_RECIPES, definition = "update_recipes")
public class PacketOutUpdateRecipes extends AbstractPacket implements PacketPlayOut {
	
	public List<Recipe> recipes;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_RECIPES;
	}

}
