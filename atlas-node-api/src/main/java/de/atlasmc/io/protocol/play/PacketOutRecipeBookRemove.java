package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_RECIPE_BOOK_REMOVE, definition = "recipe_book_remove")
public class PacketOutRecipeBookRemove extends AbstractPacket implements PacketPlayOut {

	public int[] recipes;
	
	@Override
	public int getDefaultID() {
		return OUT_RECIPE_BOOK_REMOVE;
	}

}
