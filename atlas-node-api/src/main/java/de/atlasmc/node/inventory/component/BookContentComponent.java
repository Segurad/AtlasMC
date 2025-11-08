package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.chat.Filterable;
import de.atlasmc.util.annotation.NotNull;

public interface BookContentComponent<T> extends ItemComponent {

	@NotNull
	List<Filterable<T>> getPages();
	
	boolean hasPages();
	
	default boolean addPage(@NotNull T page) {
		return addPage(new Filterable<>(page));
	}
	
	default boolean addPage(@NotNull Filterable<T> page) {
		if (page == null)
			throw new IllegalArgumentException("Page can not be null!");
		return getPages().add(page);
	}
	
	default boolean removePage(@NotNull T page) {
		if (page == null)
			throw new IllegalArgumentException("Page can not be null!");
		if (hasPages()) {
			final var pages = getPages();
			final int size = pages.size();
			for (int i = 0; i < size; i++) {
				if (pages.get(i).getRaw().equals(page)) {
					pages.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
}
