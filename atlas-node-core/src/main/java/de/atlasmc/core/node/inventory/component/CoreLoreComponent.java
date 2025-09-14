package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.LoreComponent;

public class CoreLoreComponent extends AbstractItemComponent implements LoreComponent {

	private List<Chat> lore;
	
	public CoreLoreComponent(ComponentType type) {
		super(type);
	}

	@Override
	public List<Chat> getLore() {
		if (lore == null)
			lore = new ArrayList<>();
		return lore;
	}

	@Override
	public boolean hasLore() {
		return lore != null && !lore.isEmpty();
	}
	
	@Override
	public CoreLoreComponent clone() {
		CoreLoreComponent comp = (CoreLoreComponent) super.clone();
		if (lore != null) {
			comp.lore = new ArrayList<>(lore.size());
			for (Chat chat : lore)
				comp.lore.add(chat.clone());
		}
		return comp;
	}

}
