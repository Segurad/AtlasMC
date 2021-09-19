package de.atlasmc.inventory.meta.lore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.chat.component.ChatComponent;

public class SimpleLore implements Lore {

	private ArrayList<ChatComponent> lore;
	
	public SimpleLore(List<ChatComponent> lore) {
		this.lore = new ArrayList<ChatComponent>(lore);
	}

	@Override
	public Iterator<ChatComponent> iterator() {
		return lore.iterator();
	}

	@Override
	public boolean isEmpty() {
		return lore.isEmpty();
	}

	@Override
	public List<ChatComponent> getLines() {
		return lore;
	}

	@Override
	public ChatComponent getLine(int line) {
		return lore.get(line);
	}

	@Override
	public void setLine(int line, ChatComponent text) {
		lore.set(line, text);
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
