package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CRAFT_RECIPE_REQUEST)
public interface PacketInCraftRecipeRequest extends PacketPlay, PacketInbound {
	
	public byte getWindowID();
	public String getRecipe();
	public boolean getMakeAll();
	
	@Override
	default int getDefaultID() {
		return IN_CRAFT_RECIPE_REQUEST;
	}

}
