package de.atlascore.inventory.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.MapDecorationComponent;
import de.atlasmc.map.MapIcon;

public class CoreMapDecorationComponent extends AbstractItemComponent implements MapDecorationComponent {

	private Map<String, MapIcon> decorations;
	
	public CoreMapDecorationComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreMapDecorationComponent clone() {
		CoreMapDecorationComponent clone = (CoreMapDecorationComponent) super.clone();
		if (hasDecoration()) {
			clone.decorations = new HashMap<>(decorations.size());
			for (Entry<String, MapIcon> entry : decorations.entrySet()) {
				MapIcon value = entry.getValue();
				if (value != null)
					value = value.clone();
				clone.decorations.put(entry.getKey(), value);
			}
		}
		return clone;
	}

	@Override
	public Map<String, MapIcon> getDecorations() {
		if (decorations == null)
			decorations = new HashMap<>();
		return decorations;
	}

	@Override
	public boolean hasDecoration() {
		return decorations != null && !decorations.isEmpty();
	}

	@Override
	public void setDecoration(String key, MapIcon icon) {
		getDecorations().put(key, icon);
	}

	@Override
	public MapIcon getDecoration(String name) {
		return hasDecoration() ? getDecorations().get(name) : null;
	}

	@Override
	public void removeDecoration(String name) {
		if (hasDecoration())
			return;
		getDecorations().remove(name);
	}

}
