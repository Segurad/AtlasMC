package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;

public interface WritableBookContentComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:writable_book_content");
	
	List<String> getPages();
	
	boolean hasPages();
	
	void addPage(String page);
	
	void removePage(String page);
	
	WritableBookContentComponent clone();

}
