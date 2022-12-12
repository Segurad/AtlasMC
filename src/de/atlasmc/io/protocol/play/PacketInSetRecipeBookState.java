package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.recipe.BookType;

@DefaultPacketID(PacketPlay.IN_SET_RECIPE_BOOK_STATE)
public class PacketInSetRecipeBookState extends AbstractPacket implements PacketPlayIn {
	
	private BookType bookType;
	private boolean bookOpen, filterActive;
	
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
		return IN_SET_RECIPE_BOOK_STATE;
	}

}
