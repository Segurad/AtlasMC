package de.atlasmc.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_PLACE_GHOST_RECIPE)
public class PacketOutPlaceGhostRecipe extends AbstractPacket implements PacketPlayOut {
	
	public int windowID;
	public NamespacedKey recipeID;
	
	@Override
	public int getDefaultID() {
		return OUT_PLACE_GHOST_RECIPE;
	}

}
