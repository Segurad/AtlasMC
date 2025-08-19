package de.atlascore.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.WritableBookContentComponent;

public class CoreWritableBookContentComponent extends AbstractItemComponent implements WritableBookContentComponent {

	private List<String> pages;
	
	public CoreWritableBookContentComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreWritableBookContentComponent clone() {
		CoreWritableBookContentComponent clone = (CoreWritableBookContentComponent) super.clone();
		if (hasPages()) {
			clone.pages = new ArrayList<>(pages);
		}
		return clone;
	}

	@Override
	public List<String> getPages() {
		if (pages == null)
			pages = new ArrayList<>();
		return pages;
	}

	@Override
	public boolean hasPages() {
		return pages != null && !pages.isEmpty();
	}

}
