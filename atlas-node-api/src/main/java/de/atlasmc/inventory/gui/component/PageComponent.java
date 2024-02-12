package de.atlasmc.inventory.gui.component;

import java.util.List;

public interface PageComponent<E> extends Component<E> {

	int getPages();
	
	int getMaxPages();
	
	/**
	 * 
	 * @return the page index or -1
	 */
	int addPage();
	
	void addPage(int index);
	
	void removePage(int index);
	
	E[] getEntries(int index);
	
	List<E[]> getEntrieList();
}
