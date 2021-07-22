package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_CRAFT_RECIPE_RESPONSE)
public interface PacketOutCraftRecipeResponse extends PacketPlay, PacketOutbound {
	
	public int getWindowID();
	public String getRecipe();
	
	@Override
	default int getDefaultID() {
		return OUT_CRAFT_RECIPE_RESPONSE;
	}

}
