package de.atlasmc.node.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLACE_RECIPE, definition = "place_recipe")
public class PacketInPlaceRecipe extends AbstractPacket implements PacketPlayIn {
	
	public int windowID;
	public NamespacedKey recipe;
	public boolean makeAll;
	
	@Override
	public int getDefaultID() {
		return IN_PLACE_RECIPE;
	}

}
