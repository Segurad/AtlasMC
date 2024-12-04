package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;

public interface LoreComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:lore");
	
	List<Chat> getLore();
	
	boolean hasLore();
	
	void addLore(Chat chat);
	
	void removeLore(Chat chat);
	
	LoreComponent clone();

}
