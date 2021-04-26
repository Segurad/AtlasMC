package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.recipe.Recipe;

public interface PacketOutDeclareRecipes extends Packet {
	
	public List<Recipe> getRecipes();
	
	@Override
	public default int getDefaultID() {
		return 0x5A;
	}

}
