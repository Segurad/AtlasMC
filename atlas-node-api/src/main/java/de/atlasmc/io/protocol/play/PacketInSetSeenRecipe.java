package de.atlasmc.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_SEEN_RECIPE, definition = "recipe_book_seen_recipe")
public class PacketInSetSeenRecipe extends AbstractPacket implements PacketPlayIn {
	
	public NamespacedKey recipeID;
	
	@Override
	public int getDefaultID() {
		return IN_SET_SEEN_RECIPE;
	}

}
