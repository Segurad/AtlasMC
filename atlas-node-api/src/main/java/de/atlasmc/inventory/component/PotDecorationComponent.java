package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;

public interface PotDecorationComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:pot_decorations");
	
	List<NamespacedKey> getDecorations();
	
	boolean hasDecorations();
	
	void addDecoration(NamespacedKey key);
	
	void removeDecoration(NamespacedKey key);
	
	PotDecorationComponent clone();

}
