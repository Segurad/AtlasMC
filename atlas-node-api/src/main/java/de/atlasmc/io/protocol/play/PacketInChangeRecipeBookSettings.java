package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.recipe.BookType;

@DefaultPacketID(packetID = PacketPlay.IN_CHANGE_RECIPE_BOOK_SETTINGS, definition = "recipe_book_settings")
public class PacketInChangeRecipeBookSettings extends AbstractPacket implements PacketPlayIn {
	
	public BookType bookType;
	public boolean bookOpen;
	public boolean filterActive;
	
	@Override
	public int getDefaultID() {
		return IN_CHANGE_RECIPE_BOOK_SETTINGS;
	}

}
