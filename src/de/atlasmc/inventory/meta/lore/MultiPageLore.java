package de.atlasmc.inventory.meta.lore;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.chat.component.ChatComponent;

public class MultiPageLore implements Lore {
	
	private List<List<ChatComponent>> pages;

	@Override
	public Iterator<ChatComponent> iterator() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return pages.isEmpty();
	}

	@Override
	public List<ChatComponent> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatComponent getLine(int line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLine(int line, ChatComponent text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countLines() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addLine(ChatComponent readStringTag) {
		// TODO Auto-generated method stub
		
	}
	
	

}
