package de.atlasmc.inventory.meta.lore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleLore implements Lore {

	private ArrayList<String> lore;
	
	public SimpleLore(List<String> lore) {
		this.lore = new ArrayList<String>(lore);
	}

	@Override
	public Iterator<String> iterator() {
		return lore.iterator();
	}

	@Override
	public boolean isEmpty() {
		return lore.isEmpty();
	}

	@Override
	public List<String> getLines() {
		return lore;
	}

	@Override
	public String getLine(int line) {
		return lore.get(line);
	}

	@Override
	public void setLine(int line, String text) {
		lore.set(line, text);
	}

	@Override
	public int countLines() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addLine(String readStringTag) {
		// TODO Auto-generated method stub
		
	}

}
