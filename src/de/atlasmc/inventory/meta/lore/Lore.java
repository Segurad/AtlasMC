package de.atlasmc.inventory.meta.lore;

import java.util.List;

public interface Lore extends Iterable<String> {

	public boolean isEmpty();
	public List<String> getLines();
	public String getLine(int line);
	public void setLine(int line, String text);
	public int countLines();
	public void addLine(String text);

}
