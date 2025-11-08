package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Filterable;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.WritableBookContentComponent;

public class CoreWritableBookContentComponent extends CoreBookContentComponent<String> implements WritableBookContentComponent {

	private List<Filterable<String>> pages;
	
	public CoreWritableBookContentComponent(ComponentType type) {
		super(type);
	}

	@Override
	public List<Filterable<String>> getPages() {
		if (pages == null)
			pages = new ArrayList<>();
		return pages;
	}

	@Override
	public boolean hasPages() {
		return pages != null && !pages.isEmpty();
	}

}
