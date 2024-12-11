package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.recipe.BookType;

@DefaultPacketID(packetID = PacketPlay.IN_CHANGE_RECIPE_BOOK_SETTINGS)
public class PacketInChangeRecipeBookSettings extends AbstractPacket implements PacketPlayIn {
	
	private BookType bookType;
	private boolean bookOpen;
	private boolean filterActive;
	
	public BookType getBookType() {
		return bookType;
	}
	
	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
	
	public boolean isBookOpen() {
		return bookOpen;
	}
	
	public void setBookOpen(boolean bookOpen) {
		this.bookOpen = bookOpen;
	}
	
	public boolean isFilterActive() {
		return filterActive;
	}
	
	public void setFilterActive(boolean filterActive) {
		this.filterActive = filterActive;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CHANGE_RECIPE_BOOK_SETTINGS;
	}

}
