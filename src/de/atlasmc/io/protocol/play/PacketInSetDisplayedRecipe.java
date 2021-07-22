package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_SET_DISPLAYED_RECIPE)
public interface PacketInSetDisplayedRecipe extends PacketPlay, PacketInbound {
	
	public String getRecipeID();
	
	@Override
	default int getDefaultID() {
		return IN_SET_DISPLAYED_RECIPE;
	}

}
