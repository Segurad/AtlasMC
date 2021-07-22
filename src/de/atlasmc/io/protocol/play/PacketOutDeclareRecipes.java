package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.recipe.Recipe;

@DefaultPacketID(PacketPlay.OUT_DECLARE_RECIPES)
public interface PacketOutDeclareRecipes extends PacketPlay, PacketOutbound {
	
	public List<Recipe> getRecipes();
	
	@Override
	public default int getDefaultID() {
		return 0x5A;
	}

}
