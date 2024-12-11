package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_EDIT_BOOK)
public class PacketInEditBook extends AbstractPacket implements PacketPlayIn {
	
	private int slot;
	private String title;
	private String[] pages;
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setPages(String[] pages) {
		this.pages = pages;
	}
	
	public String[] getPages() {
		return pages;
	}
	
	@Override
	public int getDefaultID() {
		return IN_EDIT_BOOK;
	}

}
