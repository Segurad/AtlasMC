package de.atlasmc.inventory.meta.lore;

import java.util.Iterator;
import java.util.List;

public class MultiPageLore implements Lore {
	
	private List<List<String>> pages;

	@Override
	public Iterator<String> iterator() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return pages.isEmpty();
	}

	@Override
	public List<String> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLine(int line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLine(int line, String text) {
		// TODO Auto-generated method stub
		
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
