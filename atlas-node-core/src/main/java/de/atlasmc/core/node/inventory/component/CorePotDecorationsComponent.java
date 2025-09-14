package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.PotDecorationComponent;

public class CorePotDecorationsComponent extends AbstractItemComponent implements PotDecorationComponent {

	private List<NamespacedKey> decorations;
	
	public CorePotDecorationsComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CorePotDecorationsComponent clone() {
		CorePotDecorationsComponent clone = (CorePotDecorationsComponent) super.clone();
		if (hasDecorations()) {
			clone.decorations = new ArrayList<>(decorations);
		}
		return clone;
	}

	@Override
	public List<NamespacedKey> getDecorations() {
		if (decorations == null)
			decorations = new ArrayList<>();
		return decorations;
	}

	@Override
	public boolean hasDecorations() {
		return decorations != null && !decorations.isEmpty();
	}

	@Override
	public void addDecoration(NamespacedKey key) {
		getDecorations().add(key);
	}

	@Override
	public void removeDecoration(NamespacedKey key) {
		if (hasDecorations())
			getDecorations().remove(key);
	}

}
