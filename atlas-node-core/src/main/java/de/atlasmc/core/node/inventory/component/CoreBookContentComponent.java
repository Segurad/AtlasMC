package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Filterable;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BookContentComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBookContentComponent<T> extends AbstractItemComponent implements BookContentComponent<T> {

	protected List<Filterable<T>> pages;
	
	public CoreBookContentComponent(ComponentType type) {
		super(type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CoreBookContentComponent<T> clone() {
		var clone = (CoreBookContentComponent<T>) super.clone();
		if (hasPages()) {
			clone.pages = new ArrayList<>(pages.size());
			for (var page : pages) {
				clone.pages.add(page.clone());
			}
		}
		return clone;
	}

	@Override
	public List<Filterable<T>> getPages() {
		if (pages == null)
			pages = new ArrayList<>();
		return pages;
	}

	@Override
	public boolean hasPages() {
		return pages != null && !pages.isEmpty();
	}

}
