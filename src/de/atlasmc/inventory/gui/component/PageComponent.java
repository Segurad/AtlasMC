package de.atlasmc.inventory.gui.component;

import java.util.List;

public interface PageComponent<E> extends Component<E> {

	public int getPages();
	public int getMaxPages();
	
	/**
	 * 
	 * @return the page index or -1
	 */
	public int addPage();
	public void addPage(int index);
	public void removePage(int index);
	public E[][] getEntries(int index);
	public List<E[][]> getEntrieList();
}
